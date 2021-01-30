package com.wang.service;

import com.wang.NotFoundException;
import com.wang.dao.TypeRepository;
import com.wang.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    //保存
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    //查询
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    //分页查询
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }



    //更新
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t =  typeRepository.getOne(id);
        if (t == null){
            try {
                throw new NotFoundException("该类型不存在");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }


    //删除
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }



    @Override
    public Type getTypeName(String name) {

        return typeRepository.findByName(name);
    }



    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }




    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

}
