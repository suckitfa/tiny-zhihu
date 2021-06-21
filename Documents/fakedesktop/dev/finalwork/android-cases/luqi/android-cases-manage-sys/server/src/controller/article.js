const { exec } = require('../db/mysql');
const getList = (keyword) => {
    // 注意这条语句的处理 
    let sql = `select * from articles where 1=1 `;
    if (keyword) {
        sql += ` and keyword like '%${keyword}%' `
    }
    sql += `order by createtime asc;`;
    // 返回的为promise
    return exec(sql);
}

const getDetail = (id) => {
    const sql = `select * from articles where articleid='${id}'`;
    return exec(sql).then(rows => {
        return rows[0];
    });
}

const newArticle = (articleData = {}) => {
    // 管理员才有权限
    const { userid, keyword, content } = articleData;
    const createtime = Date.now();
    const sql = `
    insert into 
    articles(userid,keyword,createtime,content) 
    values('${userid}','${keyword}',${createtime},'${content}');`;
    return exec(sql).then(insertData => {
        console.log(insertData);
        return {
            id: insertData.insertId
        };
    });
}

// 更新的话就是更新标题和内容
const updateArticle = (articleid, articleData = {}) => {
    const { userid, content } = articleData;

    const sql = `
    update articles set userid='${userid}', content='${content}'where articleid=${articleid};
    `
    return exec(sql).then(updateData => {
        if (updateData.affectedRows > 0) {
            return true;
        }
        return false;
    });
}


// 根据article删除文章
const deleteArticle = (articleid) => {
    const sql = `delete from articles where articleid=${articleid};`;
    return exec(sql).then(delData => {
        if (delData.affectedRows > 0) {
            return true;
        }
        return false;
    });
}
module.exports = {
    getList,
    getDetail,
    newArticle,
    updateArticle,
    deleteArticle
}