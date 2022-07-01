/**
 * http访问工具类
 */

import axios from 'axios';
import { promised, reject } from 'q';
axios.defaults.timeout = 5000;  // 超时时间5秒
axios.defaults.withCredentials = true;  //允许跨域
// 响应头
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// 基础url
axios.defaults.baseURL = "http://localhost:8888";
// 响应拦截器（用户是否以登录 返回的是code是0or1
axios.interceptors.response.use(
  // 访问成功
  response => {
    // 如果response.status是200说明登录成功，否则错误
    if(response.status == 200){
      return Promise.resolve(response);
    }else{
      return Promise.reject(response);
    }
  },
  // 访问失败
  error => {
    if(error.response.status){
      switch(error.response.status){
        case 401: //未登录
          router.replace({  //重定向到根目录（登录页面）
            path:'/',
            query:{ //存一下当前的地址
              redirect: router.currentRoute.fullPath
            }
          });
          break;
        case 404: //没找到
          break;
      }
      return Promise.reject(error.response)
    }
  }
);

/**
 * 封装get方法,get中params是参数用一个个&符号连接起来，post里data是用表单提交的纯对象
 */
export function get(url,params={}){
  return new Promise((resolve,reject) => {
    axios.get(url,{params:params})
    .then(response =>{
      resolve(response.data);
    })
    .catch(err =>{
      reject(err)
    })
  })
}

/**
 * 封装post方法
 */
export function post(url,data={}){
  return new Promise((resolve,reject) => {
    axios.post(url,data)
    .then(response =>{
      resolve(response.data);
    })
    .catch(err =>{
      reject(err)
    })
  })
}
