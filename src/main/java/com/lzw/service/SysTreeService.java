package com.lzw.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.lzw.dao.SysAclModuleMapper;
import com.lzw.dao.SysDeptMapper;
import com.lzw.dto.AclModuleLevelDto;
import com.lzw.dto.DeptLevelDto;
import com.lzw.model.SysAclModule;
import com.lzw.model.SysDept;
import com.lzw.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;


    public List<AclModuleLevelDto> aclModuleTree(){
        List<SysAclModule> aclModuleList =sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for(SysAclModule aclModule : aclModuleList){
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return  aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree (List<AclModuleLevelDto> dtoList){
        if (CollectionUtils.isNotEmpty(dtoList)){
            return Lists.newArrayList();
        }
        // level -> [dept1,dept2,...]  map<String,list<object>>
        Multimap<String, AclModuleLevelDto> levelMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList){
            // 全部部门按级别存储到map中
            levelMap.put(dto.getLevel(),dto);
            // 把是头的部门先记录下来
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        // 排序
        Collections.sort(rootList,aclModuleSeqComparator);

        transformAclModuleTree(rootList,LevelUtil.ROOT,levelMap);

        return  rootList;
    }


    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList,String level,Multimap<String,AclModuleLevelDto> levelAclModuleMap){
        for (int i = 0; i<dtoList.size();i++){
            // 遍历该层的每个元素
            AclModuleLevelDto dto = dtoList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level,dto.getId());
            // 处理下一层
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempList)){
                // 排序
                Collections.sort(tempList,aclModuleSeqComparator);
                // 设置下一层部门
                dto.setAclModuleList(tempList);
                // 进入到下一层处理
                transformAclModuleTree(tempList,nextLevel,levelAclModuleMap);
            }
        }
    }


    //---------------部门的-----------------


    public List<DeptLevelDto> deptTree(){
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList =Lists.newArrayList();
        for (SysDept dept : deptList){
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);// 全部部门封装成一件马甲
        }
        return  deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList){
        // 判断是否空
        if (CollectionUtils.isEmpty(deptLevelList)){
            return Lists.newArrayList();
        }

        // level -> [dept1,dept2,...]
        Multimap<String,DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList){
            // 全部部门按级别存储到map中
            levelDeptMap.put(dto.getLevel(),dto);
            // 把是头的部门先记录下来
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // 递归生成树
        transformDeptTree(deptLevelList,LevelUtil.ROOT,levelDeptMap);
        List<DeptLevelDto> ld= rootList;
        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelList,String level,Multimap<String,DeptLevelDto> levelDeptMap){
        for (int i = 0; i<deptLevelList.size();i++){
            // 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level,deptLevelDto.getId());
            // 处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempDeptList)){
                // 排序
                Collections.sort(tempDeptList,deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList,nextLevel,levelDeptMap);
            }
        }
    }

    public  Comparator<DeptLevelDto> deptSeqComparator =new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };

    public  Comparator<AclModuleLevelDto> aclModuleSeqComparator =new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
}
