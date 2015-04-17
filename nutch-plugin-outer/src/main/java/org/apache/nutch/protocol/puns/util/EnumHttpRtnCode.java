package org.apache.nutch.protocol.puns.util;

public enum EnumHttpRtnCode {
	OK, // OK 200
	FAILCONNPROXY,	// 988 连接代理服务器失败
	MAXCONN,	// 987 代理服务器超过最大连接数
	REDIRECT, // 重定向 300-399
	BADREQUEST,   // 坏的请求 400
	NOTFOUND, // 页面没找到 404
	NOAUTH, // 需要认证 401
    PAGEISGONE,   // 页面被迁移 410 
    INTERNALERROR, // 内部错误 500
    NOTAVALAIBLE, // 服务不可用 501
    OTHERS,		  // 其他code
    NOFETCHYET,   // 还没抓取
    SocketTimeoutException, // Socket读取超时Exception
    Exception ,		// Exception
    MRWriteException, // mapreduce写入错误
    
    
}
