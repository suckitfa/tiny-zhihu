const querystring = require('querystring');
const { access } = require('./src/utils/log');
const handleBlogRouter = require('./src/router/article');
const handleUserRouter = require('./src/router/user');
const handleInfoRouter = require('./src/router/info');
// session数据
const SESSION_DATA = {}
    // 获取cookie的过期事件
const getCookieExpires = () => {
    const d = new Date();
    d.setTime(d.getTime() + (24 * 60 * 60 * 1000));
    console.log(d.toGMTString());
    return d.toGMTString();
};

// 用于处理post data
const getPostData = (req) => {
    const promise = new Promise((resolve, reject) => {
        if (req.method !== 'POST') {
            resolve({});
            return;
        }
        // 保证传入的数据格式为json
        if (req.headers['content-type'] !== 'application/json') {
            resolve({});
            return;
        }
        let postData = "";
        req.on('data', chunk => {
            postData += chunk.toString();
        });
        req.on('end', () => {
            if (!postData) {
                resolve({});
                return;
            }
            resolve(JSON.parse(postData));
        });
    });
    return promise;
}


const serverHandle = (req, res) => {
    // 记录access log
    access(`${req.method} -- ${req.url} -- ${req.headers['user-agent']} -- ${Date.now()}`);
    // 保证返回的数据为JSON
    res.setHeader('content-type', 'application/json');

    // 获取url,path
    const url = req.url;
    req.path = url.split('?')[0];

    // 解析query参数
    req.query = querystring.parse(url.split('?')[1]);
    req.cookie = {};
    // 解析cookie
    const cookieString = req.headers.cookie || '';
    cookieString.split(';').forEach(item => {
        if (!item) {
            return;
        }
        const arr = item.split('=');
        const [key, val] = arr;
        req.cookie[key.trim()] = val.trim();
    });

    // 解析session
    let needSetCookie = false;
    let userId = req.cookie.userid;
    if (userId) {
        if (!SESSION_DATA[userId]) {
            SESSION_DATA[userId] = {};
        }
    } else {
        needSetCookie = true;
        userId = `${Date.now()}_${Math.random()}`;
        SESSION_DATA[userId] = {};
    }
    req.session = SESSION_DATA[userId];
    // 处理postData之后，在then中处理对应的路由
    getPostData(req).then(postData => {
        req.body = postData;
        // 处理blog路由,并返回对应数据
        const blogResult = handleBlogRouter(req, res);
        if (blogResult) {
            blogResult.then(blogData => {
                if (blogData) {
                    if (needSetCookie) {
                        res.setHeader('Set-Cookie', `userid=${userId};path=/; httpOnly; expires=${getCookieExpires()}`);
                    }
                    res.end(JSON.stringify(blogData));
                }
            });
            return;
        }


        // 处理user路由
        const userResult = handleUserRouter(req, res);
        if (userResult) {
            userResult.then(userData => {
                if (userData) {
                    if (needSetCookie) {
                        res.setHeader('Set-Cookie', `userid=${userId};path=/; httpOnly; expires=${getCookieExpires()}`);
                    }
                    res.end(
                        JSON.stringify(userData)
                    );
                }
            });
            return;
        }
        const infoResult = handleInfoRouter(req, res);
        if (infoResult) {
            infoResult.then(infoData => {
                if (infoData) {
                    if (needSetCookie) {
                        res.setHeader('Set-Cookie', `userid=${userId};path=/; httpOnly; expires=${getCookieExpires()}`);
                    }
                    res.end(
                        JSON.stringify(infoData)
                    );
                }
            });
            return;
        }


        // 未命中路由返回404
        res.writeHead(404, {
            "content-type": "text/plain"
        });
        res.write("404 not found\n");
        res.end();
    });
}
module.exports = serverHandle;