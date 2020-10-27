package com.intertek.demo.common.exception;

/**
 * 文件下载异常
 *
 * @author jacksy.qin
 * @date 2019/9/23 16:44
 */
public class FileDownloadException extends Exception {

    private static final long serialVersionUID = 7915779410331701259L;

    public FileDownloadException(String message) {
        super(message);
    }
}
