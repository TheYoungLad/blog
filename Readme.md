## 博客系统

博客地址：[http://xinglongfei.cn/](http://xinglongfei.cn/)

### 写在前面

之前我的博客网站是用的github+hexo方式搭建的，最近打算自己通过代码来搭建博客，花了一个多礼拜的时间，把项目的基本功能模块搭建起来了，后续还会持续更新，最近在准备面试以及备战明年春招，争取每周为这个博客添砖加瓦，并且修复一些bug，其实现在也在准备着手思索博客系统2.0怎么实现，不过由于时间关系，应该得明年或者找到工作稳定下来后再去实现大版本更新了，不过在这之前，我会不断地去完善这个博客系统，实现小版本的更新。




### 1.项目使用的技术
* Springboot
* Spring Data JPA
* Maven
* semanticUI实现


### 2.项目使用到的插件
* [自动生成目录插件：Tocbot](https://tscanlin.github.io/tocbot/)
* [代码高亮插件：prismjs](https://prismjs.com/)
* [中文网页重设与排版插件：typo.css](https://typo.sofi.sh/)
* [动画插件：ANIMATE.CSS](http://www.animate.net.cn/)
* [开源在线 Markdown 编辑器: editor.md](https://pandao.github.io/editor.md/)
* [jQuery定位跳转插件：jquery.scrollto.js](https://github.com/flesler/jquery.scrollTo)


### 3.博客功能
#### 前端展示
* 展示博客分页列表
* 展示最新博客
* 展示博客详情
* 展示所有分类
* 展示某分类下的所有博客
* 展示所有标签
* 展示某标签下的所有博客
* 展示博客归档列表
* 展示博客评论
* 访客进行评论

#### 后台管理
* 管理员登录
* 博客管理（新增、编辑、查找、删除）
* 分类管理（新增、编辑、删除）
* 标签管理（新增、编辑、删除）


### 4.更新日志

#### V1.0.5(未完善)———2020/11/24
##### **新增/完善**

* 引入阿里云OSS作为图床来保存项目图片，以获取更快的图片加载速度，也便于管理图片
* 加入书签功能完善（不支持自动添加书签的浏览器会进行弹出提示如何手动加入书签）

**修复**

* 修复后台管理页面操作结果”成功/失败“弹窗对小屏用户不友好的问题

* 修复后台管理页面”点击返回按钮返回上一页时会出现上次的操作状态弹窗”的问题



####  V1.0————————2020/11/24
* 博客系统正式上线
