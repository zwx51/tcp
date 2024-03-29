package com.ril.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ril.bean.TCPRecord;
import com.ril.bean.TCPRecordExample;

public interface TCPRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    long countByExample(TCPRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int deleteByExample(TCPRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int insert(TCPRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int insertSelective(TCPRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    List<TCPRecord> selectByExample(TCPRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    TCPRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TCPRecord record, @Param("example") TCPRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TCPRecord record, @Param("example") TCPRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TCPRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcprecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TCPRecord record);
    /*
     * 非自动生成部分
     * */
    @Select("select " +
    		"((max(count)-min(count))/NULLIF(((DATEDIFF(SECOND,  min(recordtime),max(recordtime))*1.0)/3600),0))" +
    		" from[tcprecord] "+
    		"where noc = #{noc} and stocknumber= #{stocknumber} and machid= #{machid} and "+
    		"mode= #{mode}")
    Double getSpeed(@Param("noc")String noc,
    		@Param("stocknumber")String stocknumber,
    		@Param("machid")long machid,@Param("mode")int mode);
    
    
    @Select("Select max(count)as count, " +
    		"max(recordtime)as endtime, min(recordtime) as starttime " +
    		"from [tcprecord] " +
    		"where mode = #{mode} and noc = #{noc} " +
    		"and stocknumber = #{stocknumber} and machid = #{machid} " +
    		"having max(recordtime) between #{endFront} and #{endBack} " +
    		"and min(recordtime) between #{startFront} and #{startBack}")
    Map<String,String> selectHistorybyTime(
    		@Param("noc")String noc,
    		@Param("stocknumber")String stocknumber,
    		@Param("machid")long machid,@Param("mode")int mode,
    		@Param("endFront")Date endFront,@Param("endBack")Date endBack,
    		@Param("startFront")Date startFront,@Param("startBack")Date startBack);
}