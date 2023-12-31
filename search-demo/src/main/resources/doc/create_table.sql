create database `prac-search-demo`;
use `prac-search-demo`;

create table tb_article
(
    id      bigint      not null auto_increment primary key,
    title   varchar(64) not null comment '标题',
    content text        not null comment '内容'
) comment '文章表';


insert into tb_article (title, content)
VALUES ('健康饮食指南',
        '饮食对我们的健康至关重要。本文将分享一些健康饮食的指南，包括均衡饮食、适量摄入各类营养素、选择新鲜食材、限制加工食品等方面的建议。'),
       ('如何学习编程',
        '编程是一项有趣且富有挑战性的技能。本文将介绍学习编程的基本步骤和方法，包括选择编程语言、掌握编程概念、参与实践项目、寻找资源和社区支持等。'),
       ('旅行的必备物品清单',
        '计划旅行时，准备充分的必备物品清单可以确保您在旅途中拥有愉快的体验。本文列举了旅行必备物品，例如护照、行李箱、充电器、药品等，以帮助您做好出行准备。'),
       ('如何建立良好的沟通技巧',
        '良好的沟通技巧对于个人和职业发展都至关重要。本文将介绍一些改进沟通技巧的方法，包括倾听他人、表达清晰、掌握非语言沟通等方面的建议。'),
       ('如何管理个人财务',
        '有效管理个人财务可以帮助您实现财务目标并确保经济安全。本文将分享一些管理个人财务的方法，如制定预算、储蓄、投资规划、消费控制等。'),
       ('养成良好的阅读习惯',
        '阅读是一种重要的学习和娱乐方式。本文将提供一些建立良好阅读习惯的建议，例如每天设定阅读时间、选择适合自己的读物、记录读书笔记等。'),
       ('如何处理压力',
        '在现代生活中，压力是常态化的。本文将介绍一些处理压力的方法，包括放松技巧、锻炼身体、寻求支持系统、积极思维等，帮助您更好地应对压力。'),
       ('职场礼仪指南',
        '在职场上展现良好的职业素养和礼仪是成功的关键之一。本文将介绍一些职场礼仪的指南，包括着装得体、尊重他人、有效沟通等方面的建议。'),
       ('如何培养创造力',
        '创造力是在各个领域取得成功的重要因素。本文将分享一些培养创造力的方法，如开放思维、接触新事物、创造性思维训练等，帮助您发展自己的创造潜力。')