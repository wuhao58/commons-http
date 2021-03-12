###  1. 组件列表
|  组件   | 版本  | 说明  |
|  ----  | ----  | ----  |
| spring-boot  | 2.2.5.RELEASE |   |
| fastjson  | 1.2.60 |   |
| okhttp  | 3.14.7 |  okhttp3 |


###  2. 需求解决
- Java服务端调用http网络请求，封装okhttp，让调用更简单。


###  3. 更换json解析
- 默认使用jackson进行解析。
- 如果想使用fastjson，更换JacksonUtil.class为FastjsonUtil.class即可。
