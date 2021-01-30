package com.wang.service;

import com.wang.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Charlie Puth
 * @version 1.0
 **/
public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    Tag getTagName(String name);
}
