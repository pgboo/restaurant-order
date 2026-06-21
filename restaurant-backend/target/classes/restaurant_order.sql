-- 餐饮管理系统数据库初始化脚本
CREATE DATABASE IF NOT EXISTS restaurant_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE restaurant_order;

-- 管理员表
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

-- 用户表
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机',
    member_level VARCHAR(20) DEFAULT '普通会员' COMMENT '会员等级',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 员工表
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) DEFAULT NULL COMMENT '姓名',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工';

-- 菜品类型表
DROP TABLE IF EXISTS caipin_type;
CREATE TABLE caipin_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '类型名称',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品类型';

-- 菜品表
DROP TABLE IF EXISTS caipin;
CREATE TABLE caipin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '菜品名称',
    type_id BIGINT DEFAULT NULL COMMENT '类型ID',
    price DECIMAL(10,2) DEFAULT NULL COMMENT '价格',
    image VARCHAR(255) DEFAULT NULL COMMENT '图片',
    description TEXT COMMENT '描述',
    status VARCHAR(20) DEFAULT '下架' COMMENT '状态(上架/下架)',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品';

-- 菜品订单表
DROP TABLE IF EXISTS caipin_order;
CREATE TABLE caipin_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL COMMENT '订单编号',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    caipin_id BIGINT DEFAULT NULL COMMENT '菜品ID',
    quantity INT DEFAULT 1 COMMENT '数量',
    total_price DECIMAL(10,2) DEFAULT NULL COMMENT '总价',
    status VARCHAR(20) DEFAULT '待处理' COMMENT '状态',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品订单';

-- 菜品评价表
DROP TABLE IF EXISTS caipin_comment;
CREATE TABLE caipin_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    caipin_id BIGINT DEFAULT NULL COMMENT '菜品ID',
    order_id BIGINT DEFAULT NULL COMMENT '订单ID',
    content TEXT COMMENT '评价内容',
    reply TEXT COMMENT '回复内容',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品评价';

-- 菜品收藏表
DROP TABLE IF EXISTS caipin_collect;
CREATE TABLE caipin_collect (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    caipin_id BIGINT DEFAULT NULL COMMENT '菜品ID',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品收藏';

-- 供应商表
DROP TABLE IF EXISTS gongyingshang;
CREATE TABLE gongyingshang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '供应商名称',
    contact VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    phone VARCHAR(20) DEFAULT NULL COMMENT '电话',
    address VARCHAR(255) DEFAULT NULL COMMENT '地址',
    description TEXT COMMENT '描述',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商';

-- 公告类型表
DROP TABLE IF EXISTS gonggao_type;
CREATE TABLE gonggao_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '类型名称',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告类型';

-- 公告表
DROP TABLE IF EXISTS gonggao;
CREATE TABLE gonggao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    type_id BIGINT DEFAULT NULL COMMENT '类型ID',
    content TEXT COMMENT '内容',
    image VARCHAR(255) DEFAULT NULL COMMENT '图片',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告';

-- 论坛帖子表
DROP TABLE IF EXISTS forum;
CREATE TABLE forum (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    content TEXT COMMENT '内容',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='论坛';

-- 论坛回复表
DROP TABLE IF EXISTS forum_reply;
CREATE TABLE forum_reply (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    forum_id BIGINT DEFAULT NULL COMMENT '帖子ID',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    content TEXT COMMENT '回复内容',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='论坛回复';

-- 物品类型表
DROP TABLE IF EXISTS wupin_type;
CREATE TABLE wupin_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '类型名称',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品类型';

-- 会员等级表
DROP TABLE IF EXISTS member_level;
CREATE TABLE member_level (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '等级名称',
    discount DECIMAL(3,2) DEFAULT 1.00 COMMENT '折扣',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级';

-- 单页数据类型表
DROP TABLE IF EXISTS danpage_type;
CREATE TABLE danpage_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '类型名称',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单页数据类型';

-- 单页数据表
DROP TABLE IF EXISTS danpage;
CREATE TABLE danpage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    type_id BIGINT DEFAULT NULL COMMENT '类型ID',
    content TEXT COMMENT '内容',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单页数据';

-- 公司信息表
DROP TABLE IF EXISTS company;
CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '公司名称',
    address VARCHAR(255) DEFAULT NULL COMMENT '地址',
    phone VARCHAR(20) DEFAULT NULL COMMENT '电话',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    description TEXT COMMENT '描述',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息';

-- 轮播图表
DROP TABLE IF EXISTS lunbo;
CREATE TABLE lunbo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) DEFAULT NULL COMMENT '标题',
    image VARCHAR(255) DEFAULT NULL COMMENT '图片',
    url VARCHAR(255) DEFAULT NULL COMMENT '链接',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图';

-- 留言反馈表
DROP TABLE IF EXISTS feedback;
CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    content TEXT COMMENT '内容',
    reply TEXT COMMENT '回复',
    addtime VARCHAR(50) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言反馈';

-- ========== 初始数据 ==========

-- 管理员 (密码: admin123, MD5: 0192023a7bbd73250516f069df18b500)
INSERT INTO admin (username, password, addtime) VALUES ('admin', '0192023a7bbd73250516f069df18b500', '2024-01-01 00:00:00');

-- 默认用户 (密码: 123456, MD5: e10adc3949ba59abbe56e057f20f883e)
INSERT INTO users (username, password, nickname, phone, member_level, addtime) VALUES
('user1', 'e10adc3949ba59abbe56e057f20f883e', '张三', '13800138001', '普通会员', '2024-01-01 00:00:00'),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '李四', '13800138002', '普通会员', '2024-01-02 00:00:00');

-- 默认员工 (密码: 123456)
INSERT INTO employee (username, password, name, phone, addtime) VALUES
('emp1', 'e10adc3949ba59abbe56e057f20f883e', '王五', '13900139001', '2024-01-01 00:00:00'),
('emp2', 'e10adc3949ba59abbe56e057f20f883e', '赵六', '13900139002', '2024-01-02 00:00:00');

-- 菜品类型
INSERT INTO caipin_type (name, addtime) VALUES
('川菜', '2024-01-01 00:00:00'),
('粤菜', '2024-01-01 00:00:00'),
('湘菜', '2024-01-01 00:00:00'),
('鲁菜', '2024-01-01 00:00:00'),
('西餐', '2024-01-01 00:00:00'),
('日料', '2024-01-01 00:00:00');

-- 菜品
INSERT INTO caipin (name, type_id, price, image, description, status, addtime) VALUES
('麻婆豆腐', 1, 28.00, NULL, '经典川菜，麻辣鲜香', '上架', '2024-01-01 00:00:00'),
('宫保鸡丁', 1, 38.00, NULL, '传统川菜，鸡肉嫩滑', '上架', '2024-01-01 00:00:00'),
('回锅肉', 1, 42.00, NULL, '四川名菜，肥而不腻', '上架', '2024-01-01 00:00:00'),
('白切鸡', 2, 58.00, NULL, '广东名菜，皮滑肉嫩', '上架', '2024-01-01 00:00:00'),
('烧鹅', 2, 68.00, NULL, '广式烧味，皮脆肉嫩', '上架', '2024-01-01 00:00:00'),
('剁椒鱼头', 3, 58.00, NULL, '湘菜代表，辣而鲜美', '上架', '2024-01-01 00:00:00'),
('糖醋鲤鱼', 4, 48.00, NULL, '鲁菜经典，酸甜可口', '上架', '2024-01-01 00:00:00'),
('牛排', 5, 128.00, NULL, '进口牛排，五分熟', '上架', '2024-01-01 00:00:00'),
('三文鱼刺身', 6, 88.00, NULL, '新鲜三文鱼，日式刺身', '上架', '2024-01-01 00:00:00'),
('水煮鱼', 1, 68.00, NULL, '麻辣鲜香，鱼肉嫩滑', '上架', '2024-01-01 00:00:00');

-- 会员等级
INSERT INTO member_level (name, discount, addtime) VALUES
('普通会员', 1.00, '2024-01-01 00:00:00'),
('银卡会员', 0.95, '2024-01-01 00:00:00'),
('金卡会员', 0.90, '2024-01-01 00:00:00'),
('钻石会员', 0.85, '2024-01-01 00:00:00');

-- 公告类型
INSERT INTO gonggao_type (name, addtime) VALUES
('系统公告', '2024-01-01 00:00:00'),
('活动公告', '2024-01-01 00:00:00'),
('菜品公告', '2024-01-01 00:00:00');

-- 公告
INSERT INTO gonggao (title, type_id, content, addtime) VALUES
('欢迎光临本餐厅', 1, '感谢您选择我们的餐厅，我们将竭诚为您提供优质的服务和美味的菜品。', '2024-01-01 00:00:00'),
('新品上线通知', 3, '本店新推出多款特色菜品，欢迎品尝！', '2024-01-15 00:00:00');

-- 单页数据类型
INSERT INTO danpage_type (name, addtime) VALUES
('酒店介绍', '2024-01-01 00:00:00'),
('关于我们', '2024-01-01 00:00:00');

-- 公司信息
INSERT INTO company (name, address, phone, email, description, addtime) VALUES
('美味餐厅', '北京市朝阳区建国路100号', '010-88888888', 'contact@meiwei.com', '美味餐厅成立于2010年，是一家集餐饮、娱乐、休闲为一体的综合性餐厅。我们拥有专业的厨师团队，为您提供各地特色美食。', '2024-01-01 00:00:00');

-- 物品类型
INSERT INTO wupin_type (name, addtime) VALUES
('餐具', '2024-01-01 00:00:00'),
('厨房设备', '2024-01-01 00:00:00'),
('办公用品', '2024-01-01 00:00:00');

-- 供应商
INSERT INTO gongyingshang (name, contact, phone, address, description, addtime) VALUES
('鲜蔬供应有限公司', '陈经理', '13700137001', '北京市丰台区', '提供新鲜蔬菜', '2024-01-01 00:00:00'),
('肉类供应公司', '刘经理', '13700137002', '北京市大兴区', '提供优质肉类', '2024-01-01 00:00:00');

-- 论坛帖子
INSERT INTO forum (title, user_id, content, addtime) VALUES
('推荐几道好吃的菜', 1, '大家觉得哪道菜最好吃？求推荐！', '2024-01-10 00:00:00');
