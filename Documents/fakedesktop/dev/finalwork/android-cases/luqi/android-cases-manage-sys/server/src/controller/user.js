const { exec } = require("../db/mysql");

const login = (username, password) => {
    const sql = `select username,realname from users where password='${password}' and username='${username}';`
    return exec(sql).then(rows => {
        return rows[0] || {};
    });
}

const getList = () => {
    const sql = `select userid,username,studentid,isadmin,status from users where 1=1;`;
    return exec(sql);
}

const getDetail = (userid) => {
    const sql =
        `select 
        userid,username,studentid,isadmin,status 
        from users 
        where 1=1 and 
        userid=${userid}
        ;`;
    return exec(sql);

}


const newUser = (userData) => {
    const { username, studentid, password, isadmin = 0, status = 1 } = userData;
    const sql = `insert into users (username,password,studentid,isadmin,status) values('${username}',md5('${password}'),'${studentid}',${isadmin},${status});`;
    return exec(sql).then(data => {
        if (data.effectedRows > 0) {
            return true;
        }
        return false;
    });
}

const updateUser = (userid, userData) => {
    const { username, studentid, password, isadmin = 0, status = 1 } = userData;
    const sql = `update users set 
    username='${username}',
    password=md5('${password}'),
    studentid='${studentid}' 
    where 1=1 and userid=${userid};`;
    return exec(sql).then(data => {
        if (data.effectedRows > 0) {
            return true;
        }
        return false;
    });
}

const deleteUser = (userid) => {
    const sql = `delete from users where 1=1 and userid=${userid}`;
    return exec(sql).then(data => {
        if (data.effectedRows > 0) {
            return true;
        }
        return false;
    });
}
module.exports = {
    login,
    getList,
    getDetail,
    newUser,
    updateUser,
    deleteUser
}