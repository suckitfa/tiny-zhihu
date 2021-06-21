const {
    login,
    getList,
    getDetail,
    newUser,
    updateUser,
    deleteUser
} = require('../controller/user');
const { SuccessModel, ErrorModel } = require('../model/resModel');
const handleUserRouter = (req, res) => {
    const { userid } = req.query;
    const { method, path } = req;
    const userData = req.body;
    // 登入
    if (method === 'GET' && path === '/api/user/login') {
        // const { username, password } = req.query;
        // const result = login(username, password);
        // return result.then(data => {
        //     if (data.username) {
        //         req.session.username = data.username;
        //         req.session.realname = data.realname;
        //         return new SuccessModel("登入成功！");
        //     }
        //     return new ErrorModel("登入失败！");
        // });
    }

    // 验证是否登入
    if (method === 'GET' && req.path === '/api/user/login-test') {
        // if (req.session.username) {
        //     return Promise.resolve(
        //         new SuccessModel({ username: req.session.username })
        //     );
        // }
        return Promise.resolve(new ErrorModel("登入失败!"));
    }

    // 获取全部用户
    if (method === 'GET' && req.path === '/api/user/list') {
        const result = getList();
        return result.then(listData => {
            return new SuccessModel(listData);
        });
    }

    // 获取单个用户
    if (method === 'GET' && req.path === '/api/user/detail') {
        const result = getDetail(userid);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }

    if (method === 'POST' && req.path === '/api/user/new') {
        const result = newUser(userData);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }

    if (method === 'POST' && req.path === '/api/user/update') {
        const result = updateUser(userid, userData);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }

    if (method === 'POST' && req.path === '/api/user/delete') {
        const result = deleteUser(userid);
        return result.then(data => {
            return new SuccessModel(data);
        });
    }
}

module.exports = handleUserRouter;