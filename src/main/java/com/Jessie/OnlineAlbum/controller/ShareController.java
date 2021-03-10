package com.Jessie.OnlineAlbum.controller;

import com.Jessie.OnlineAlbum.entity.Image;
import com.Jessie.OnlineAlbum.entity.Result;
import com.Jessie.OnlineAlbum.entity.Share;
import com.Jessie.OnlineAlbum.service.FolderService;
import com.Jessie.OnlineAlbum.service.ImageService;
import com.Jessie.OnlineAlbum.service.ShareService;
import com.Jessie.OnlineAlbum.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/share")
public class ShareController
{
    @Autowired
    private UserService userService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private ShareService shareService;

    @RequestMapping(value = "/download/{shareCode}/{imageid}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String shareDownload(@PathVariable int imageid, @PathVariable String shareCode, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Share thisShare = shareService.getShare(shareCode);
        if (thisShare == null)
        {
            return objectMapper.writeValueAsString(Result.error("分享不存在", 404));
        }
        Image thisImage = imageService.getImage(imageid);
        if (thisImage == null)
        {
            return objectMapper.writeValueAsString(Result.error("文件不存在", 404));
        }
        if (thisImage.getVisited() != 2)
        {
            return objectMapper.writeValueAsString(Result.error("作者可能取消了分享", 403));
        }

        if (thisShare.getShareData() != imageid && thisImage.getFid() != thisShare.getShareData())
        {
            return objectMapper.writeValueAsString(Result.error("该文件不在分享列表中（暂时无法下载文件夹）", 400));
        }
        String userPath = folderService.getFolder(userService.findUser(thisShare.getShareUser()).getUserfid()).getPath();

        try
        {
            //获取页面输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //读取文件
            byte[] bytes = FileUtils.readFileToByteArray(new File(userPath + thisImage.getPath() + thisImage.getName()));
            //向输出流写文件
            //写之前设置响应流以附件的形式打开返回值,这样可以保证前边打开文件出错时异常可以返回给前台
            response.setHeader("Content-Disposition", "attachment;filename=" + thisImage.getName());
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e)
        {
            e.printStackTrace();
            return objectMapper.writeValueAsString(Result.error("服务器发生错误", 500));
        }
        return objectMapper.writeValueAsString(Result.success("开始下载"));
    }

    @RequestMapping(value = "/getShareInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserShares(String shareCode) throws Exception
    {
        Share thisShare = shareService.getShare(shareCode);
        if (thisShare == null)
        {
            return objectMapper.writeValueAsString(Result.error("不存在的分享", 404));
        }
        List<Image> list;
        if (thisShare.getShareType() == 0)
        {
            list = imageService.getUserImages(thisShare.getShareUser(), thisShare.getShareData());
        } else
        {
            Image image = imageService.getImage(thisShare.getShareData());
            list = new ArrayList<>();
            list.add(image);
        }
        return objectMapper.writeValueAsString(list);
    }
}
