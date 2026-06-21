# 餐饮管理系统开发计划

## 技术栈
- **后端**: Java 25, Spring Boot 3.x, MyBatis-Plus, MySQL 8.0, JWT认证, Knife4j接口文档
- **前端**: Vue 3 (Composition API), Vite, Element Plus, Axios, Vue Router, Pinia
- **工具**: Maven, Lombok, Hutool工具类

## 项目结构

```
d:\cursor\restaurant-order\
├── restaurant-backend\          # Spring Boot 后端
│   ├── pom.xml
│   └── src/main/java/com/restaurant/
│       ├── config\              # 配置类(CORS, JWT, MyBatis, Knife4j)
│       ├── common\              # 通用(R, ResultCode, PageResult, 拦截器)
│       ├── entity\              # 实体类
│       ├── mapper\              # MyBatis-Plus Mapper
│       ├── service\             # 业务逻辑接口+实现
│       ├── controller\          # REST API控制器
│       └── util\                # 工具类(JWT, MD5)
├── restaurant-frontend\         # Vue 3 前端
│   ├── package.json
│   └── src/
│       ├── api\                 # Axios API请求
│       ├── router\              # Vue Router路由
│       ├── store\               # Pinia状态管理
│       ├── layout\              # 布局组件(用户/管理端)
│       ├── views\               # 页面组件
│       ├── components\          # 通用组件
│       └── utils\               # 工具(axios封装等)
```

## 数据库设计（核心表）

| 表名 | 说明 | 关键字段 |
|------|------|---------|
| admin | 管理员 | id, username, password, avatar, addtime |
| users | 用户 | id, username, password, nickname, avatar, phone, member_level, addtime |
| employee | 员工 | id, username, password, name, avatar, phone, addtime |
| caipin | 菜品 | id, name, type_id, price, image, description, status(上架/下架), addtime |
| caipin_type | 菜品类型 | id, name, addtime |
| caipin_order | 菜品订单 | id, order_no, user_id, caipin_id, quantity, total_price, status, addtime |
| caipin_comment | 菜品评价 | id, user_id, caipin_id, order_id, content, reply, addtime |
| caipin_collect | 菜品收藏 | id, user_id, caipin_id, addtime |
| gongyingshang | 供应商 | id, name, contact, phone, address, description, addtime |
| gonggao_type | 公告类型 | id, name, addtime |
| gonggao | 公告 | id, title, type_id, content, image, addtime |
| forum | 论坛帖子 | id, title, user_id, content, addtime |
| forum_reply | 论坛回复 | id, forum_id, user_id, content, addtime |
| wupin_type | 物品类型 | id, name, addtime |
| member_level | 会员等级 | id, name, discount, addtime |
| danpage_type | 单页数据类型 | id, name, addtime |
| danpage | 单页数据 | id, title, type_id, content, addtime |
| company | 公司信息 | id, name, address, phone, email, description, addtime |
| lunbo | 轮播图 | id, title, image, url, addtime |
| feedback | 留言反馈 | id, user_id, content, reply, addtime |

## 实施任务

### Task 1: 初始化后端项目
- 使用 Spring Initializr 创建 Maven 项目 (Java 25, Spring Boot 3.x)
- 添加依赖: spring-boot-starter-web, mybatis-plus-spring-boot3-starter, mysql-connector-j, lombok, knife4j-openapi3-jakarta-spring-boot-starter, jjwt, hutool-all
- 配置 `application.yml`: MySQL连接、MyBatis-Plus、文件上传路径、JWT密钥

### Task 2: 后端通用基础层
- `R.java` 统一响应封装类
- `ResultCode.java` 响应状态码枚举
- `PageResult.java` 分页结果封装
- `JwtUtil.java` JWT工具类(生成/解析/验证Token)
- `JwtInterceptor.java` JWT拦截器
- `WebConfig.java` 配置CORS和拦截器注册(排除登录接口)
- `MyBatisPlusConfig.java` 分页插件配置
- `Knife4jConfig.java` 接口文档配置

### Task 3: 后端实体类与Mapper
- 创建所有数据库实体类(Entity), 使用 `@TableName`, `@TableId`, `@TableField` 注解
- 创建对应 Mapper 接口(继承 BaseMapper)
- 创建 `src/main/resources/mapper/` 下XML映射文件(复杂SQL用)

### Task 4: 后端核心服务层 - 认证模块
- `AuthController`: 用户注册、用户登录、员工注册、员工登录、管理员登录
- `AuthService`: 登录验证逻辑, JWT Token生成, 密码MD5加密
- 返回Token和用户角色信息

### Task 5: 后端服务层 - 管理员模块 (1-20)
- `AdminController`: 管理员CRUD
- `CaipinController`: 菜品CRUD + 上架/下架
- `CaipinTypeController`: 菜品类型CRUD
- `WupinTypeController`: 物品类型CRUD
- `MemberLevelController`: 会员等级CRUD
- `GonggaoTypeController`: 公告类型CRUD
- `DanpageTypeController`: 单页数据类型CRUD
- `DanpageController`: 单页数据CRUD
- `CompanyController`: 公司信息CRUD
- `LunboController`: 轮播图CRUD
- `GongyingshangController`: 供应商CRUD
- `CaipinOrderController`(管理端): 订单查询/删除/报表
- `CaipinCommentController`(管理端): 评价查询/删除/回复
- `CaipinCollectController`(管理端): 收藏查询/删除
- `ForumController`(管理端): 论坛CRUD+回复查看
- `UsersController`: 用户管理
- `EmployeeController`: 员工管理

### Task 6: 后端服务层 - 员工模块
- 复用部分Controller，通过角色权限区分
- 菜品查询(查看详情)
- 菜品评价回复/查询
- 菜品收藏查询
- 菜品订单查询
- 论坛管理(添加/查询/查看回复)
- 供应商CRUD
- 公告查询

### Task 7: 后端服务层 - 用户模块
- 首页数据: 公告列表、菜品推荐、酒店介绍、联系我们
- 菜品列表/详情/收藏/预定
- 在线发帖/留言反馈
- 个人中心: 信息管理、我的收藏、我的订单、我的评价

### Task 8: 文件上传接口
- `FileController`: 图片上传接口，存储到本地目录，返回访问URL

### Task 9: 初始化数据库
- 创建 `restaurant_order.sql` 包含所有建表语句和初始数据(管理员账号、默认菜品类型、会员等级等)

### Task 10: 初始化前端项目
- 使用 Vite 创建 Vue 3 + TypeScript 项目
- 安装 Element Plus, Axios, Vue Router, Pinia
- 配置 Vite 代理到后端 `http://localhost:8080`
- 封装 Axios 请求拦截器(自动携带Token, 401跳转登录)
- 配置 Vue Router 路由(用户端 + 管理端)

### Task 11: 前端布局与路由
- **用户端布局**: 顶部导航栏(首页/菜品/酒店介绍/联系我们/公告) + 用户头像下拉菜单
- **管理端布局**: 左侧菜单 + 顶部栏(用户信息/退出) — 管理员和员工共用，菜单根据角色动态渲染
- 路由守卫: 未登录跳转登录页，角色权限校验

### Task 12: 前端页面 - 登录注册
- 用户登录/注册页
- 员工登录/注册页
- 管理员登录页
- 统一登录入口，根据角色跳转不同首页

### Task 13: 前端页面 - 用户端
- 首页(轮播图 + 公告 + 菜品推荐)
- 菜品列表页(搜索/分类筛选/分页)
- 菜品详情页(收藏/预定)
- 酒店介绍页(单页数据)
- 联系我们页(公司信息)
- 公告详情页
- 论坛发帖页
- 留言反馈页
- 个人中心(个人信息/我的收藏/我的订单/我的评价)

### Task 14: 前端页面 - 管理端(管理员)
- 个人中心
- 管理员管理(CRUD表格+表单)
- 菜品管理(CRUD+上架)
- 菜品评价管理(查询/删除/回复)
- 菜品收藏管理(查询/删除)
- 菜品订单管理(查询/删除/报表)
- 菜品类型管理 / 物品类型管理 / 会员等级管理
- 公告类型管理 / 公告管理
- 单页数据类型管理 / 单页数据管理
- 论坛管理 / 供应商管理 / 公司信息管理
- 轮播图管理 / 用户管理 / 员工管理

### Task 15: 前端页面 - 员工端
- 个人中心
- 菜品管理(查询/详情)
- 菜品评价管理(回复/查询)
- 菜品收藏/订单管理(查询/详情)
- 论坛管理(添加/查询/回复)
- 供应商管理(CRUD)
- 公告管理(查询/详情)

### Task 16: 联调测试与收尾
- 前后端联调
- 修复Bug
- 补充必要的参数校验和数据验证
