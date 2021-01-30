package com.wang.service;

import com.wang.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface TypeService {


    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    Type getTypeName(String name);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
