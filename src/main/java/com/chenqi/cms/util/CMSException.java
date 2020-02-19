package com.chenqi.cms.util;

/**
 * @ClassName: CMSException
 * @Description: 自定义异常
 * @author:wts
 * @date: 2019年12月17日 下午8:04:51
 */
public class CMSException extends RuntimeException {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	public CMSException() {

	}

	public CMSException(String message) {
		super(message);
	}
}
