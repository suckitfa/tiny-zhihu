const { exec } = require("../db/mysql");

const getList = () => {
    const sql = `select * from learningInfo;`;
    return exec(sql);
}

const getDetail = (infoid) => {
    const sql = `select * from learningInfo where 1=1 and infoid=${infoid}`;
    return exec(sql);

}


const newInfo = (infoData) => {
    const { userid, articleid, duration } = infoData;
    const sql = `insert into learningInfo(userid,articleid,duration) 
    values(userid=${userid},articleid=${articleid},duration=${duration});
    `;
    return exec(sql).then(data => {
        if (data.effectedRows > 0) {
            return true;
        }
        return false;
    });
}

const updateInfo = (infoid, infoData) => {
    const { userid, articleid, duration } = infoData;
    const sql = `
    update learningInfo 
    set userid=${userid},
    articleid=${articleid},
    duration=${duration}
    where 1=1 and infoid=${infoid};
    `;
    console.log('sql = ', sql);
    return exec(sql).then(data => {
        if (data.affectedRows > 0) {
            return true;
        }
        return false;
    });
}

const deleteInfo = (infoid) => {
    const sql = `delete from learningInfo where 1=1 and infoid=${infoid}`;
    return exec(sql).then(data => {
        if (data.affectedRows > 0) {
            return true;
        }
        return false;
    });
}

const addTimeInfo = (time) => {

}

module.exports = {
    getList,
    getDetail,
    newInfo,
    updateInfo,
    deleteInfo,
    addTimeInfo,
}