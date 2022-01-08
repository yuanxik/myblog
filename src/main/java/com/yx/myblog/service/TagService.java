package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TagService {

    Tag getTag(Long id);

    Page<Tag> listTags(Pageable pageable);

    Tag saveTag(Tag tag);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTag(Integer size);




}
