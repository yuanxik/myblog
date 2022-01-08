package com.yx.myblog.service;/*
    @auther
    @create ---
*/

import com.yx.myblog.dao.TagRepository;
import com.yx.myblog.exception.NotFoundException;
import com.yx.myblog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Transactional
    @Override
    public Tag getTag(Long id) {
        Tag tag = tagRepository.findById(id).get();
        return tag;
    }
    @Transactional
    @Override
    public Page<Tag> listTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        Tag save = tagRepository.save(tag);
        return save;
    }
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag newTag) {
        Tag byId = tagRepository.findById(id).get();
        if (byId==null){
            throw  new NotFoundException("标签不存在.");
        }
            //将newtag对象赋值给byid
            BeanUtils.copyProperties(newTag,byId);
            return tagRepository.save(byId);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {

        Tag byName = tagRepository.findByName(name);
        return byName;
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTag(String ids) {

        List<Long> longs = convertToList(ids);
        List<Tag> allById = tagRepository.findAllById(longs);
        return allById;
    }

    @Override
    public List<Tag> listTag(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    /*
    将字符串转化为Long集合
     */
    private List<Long> convertToList(String ids){
        ArrayList<Long> list = new ArrayList<>();
        if (!"".equals(ids)&&ids!=null){
            String[] strings = ids.split(",");
            for (String str:strings
                 ) {
                list.add(Long.parseLong(str));
            }
        }
        return list;
    }
}
