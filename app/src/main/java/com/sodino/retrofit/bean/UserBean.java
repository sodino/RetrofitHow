package com.sodino.retrofit.bean;


import java.io.Serializable;

/**
 * 用户基础信息
 * @author ZhengXiaoBin 
 *  CreateTime: 2014年8月14日
 */
public class UserBean extends BaseEn implements Serializable {

	private static final long serialVersionUID = 1L;
	public String user_id;//用户唯一标识
	public String staff_id;//（用不到，都使用user_id）
	public String account_code=""; //登录帐号
	public String person_name="";  //员工姓名
	public String nick_name="";//昵称
	public String sex;//性别(0女,1男,2未知、不填)
	public String birthday="";
	public String email="";//邮箱
	public String area="";//地区
	public String phone="";//手机
	public float credit;//学习积分
	public long gold;//金币
	public int age;//年龄（系统年份-出生年份+1）若没有出生年份，则为0
	public String post_id;//岗位ID（一个岗位可以有多个职位）
	public String post_name;//岗位名称

	public String position_id;//职位ID
	public String position_name="";//工作职位

	public String dept_id="";//所属部门的ID
	public String dept_name="";//所属部门的名称
	public int level ;//等级
	public String level_name="";//"一年级"
	public String header_url="";//头像URL
	public long exp=0l;//经验
	public long max_exp;// 最大经验
	public boolean mobile_rpt_usable;//报表权限
	public String next_level_name;
	public String personal_signature;//用户签名

	private String marital_status;//婚恋状态（0单身、1恋爱中、2已婚,3未知、没填)
	private String binded_email;//绑定邮箱
	private String binded_mobile_phone;//绑定手机

	private static UserBean mInstance;
	public static final String Tag = "UserBean";
	
}
