package pro.meisen.boot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.domain.Tag;
import pro.meisen.boot.uc.TagManage;
import pro.meisen.boot.web.res.TagVo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author meisen
 * 2019-07-14
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagManage tagManage;

    @GetMapping("/all")
    public List<TagVo> all(HttpServletRequest request) {
        List<Tag> tagList = tagManage.listAll();
        return assembleTagRs(tagList);
    }

    @DeleteMapping("/delete")
    public Boolean delete(HttpServletRequest request, @RequestParam("tagId") Long id) {
        if (null == id) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数错误,删除失败");
        }
        tagManage.deleteTag(id);
        return true;
    }

    @PutMapping("/update")
    public Boolean update(HttpServletRequest request, @RequestBody Tag tag) {
        if (null == tag || null == tag.getId()) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数错误,更新失败");
        }
        tagManage.updateTag(tag);
        return true;
    }

    private List<TagVo> assembleTagRs(List<Tag> tagList) {
        if (CollectionUtils.isEmpty(tagList)) {
            return new ArrayList<>();
        }
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVo rs = new TagVo();
            rs.setId(tag.getId());
            rs.setTagName(tag.getTagName());
            tagVoList.add(rs);
        }
        return tagVoList;
    }
}

