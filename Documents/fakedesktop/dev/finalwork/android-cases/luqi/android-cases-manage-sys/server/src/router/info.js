const {
    getList,
    getDetail,
    newInfo,
    updateInfo,
    deleteInfo,
} = require('../controller/info');
const { login } = require('../controller/user');
const { SuccessModel, ErrorModel } = require('../model/resModel');
// 验证登入的中间件
const loginCheck = (req) => {
    if (!req.session.username) {
        return Promise.resolve(new ErrorModel("尚未登入!"));
    }
}

const handleBlogRouter = (req, res) => {
    const { method, path } = req;
    const infoid = req.query.infoid;
    const infoData = req.body;

    if (method === 'GET' && path === '/api/info/list') {
        const result = getList();
        console.log(result);
        console.log("进入接口了!");
        return result.then(listData => {
            return new SuccessModel(listData);
        });
    }


    if (method === 'GET' && path === '/api/info/detail') {
        const result = getDetail(infoid);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }

    if (method === 'POST' && path === '/api/info/new') {
        const result = newInfo(infoData);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }

    if (method === 'POST' && path === '/api/info/update') {
        const result = updateInfo(infoid, infoData);
        console.log('infoid = ', infoid);
        console.log('infoData = ', infoData);
        return result.then(val => {
            console.log(val);
            if (val) {
                return new SuccessModel();
            } else {
                return new ErrorModel("更新失败!");
            }
        });
    }

    if (method === "POST" && path === '/api/info/delete') {
        const result = deleteInfo(infoid);
        return result.then(val => {
            if (val) {
                return new SuccessModel();
            } else {
                return new ErrorModel("删除学习信息失败!");
            }
        });
    }
}

module.exports = handleBlogRouter;