package com.learn.management.util;

import com.iw86.base.FileUtil;
import com.iw86.base.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <B>Function :</B> <br>
 * <B>General Usage :</B> <br>
 * <B>Special Usage :</B> <br>
 * 代码生成 工具类
 *
 * @author : HRH<br>
 * @version : v1.0
 * @since : 14:12 2018/8/28 0028<br>
 */
public class AutoGenerator {
    private static String[] intType = new String[]{"int", "tinyint", "smallint", "mediumint"};
    private DataSource dataSource;

    public AutoGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void create(String domainDir, String mapperDir, String dbTable, String dbBean, String packagePrefix) {
        String mainPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        String srcPath = mainPath + "/java/";
        String mapDir = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        String mapPath = (mapperDir == null ? mapDir + "/java/" : mapDir + mapperDir) + "/";
        String beanName = packagePrefix + ".domain." + setPascalCase(dbBean);
        this.createBean(dbTable, dbBean, srcPath, packagePrefix + ".domain");
        this.createDao(dbBean, srcPath, packagePrefix + ".dao");
        this.createSqlMap(dbTable, dbBean, mapPath, beanName, StringUtil.str(packagePrefix, ".dao.", setPascalCase(dbBean), "Dao"), "");
    }

    /**
     * @param domainDir     service模块名称,例如"up-ecommerce-order-service"
     * @param mapperDir     mapper文件存放路径,例如"resources/mybatis-mapper"
     * @param dbTable       表格名称,例如"up_bu_pay"
     * @param dbBean        同上
     * @param packagePrefix service包路径 ,例如"com.up.e.commerce.order.service"
     * @param rpcDir        rpc模块名称,例如"up-ecommerce-order-rpc"
     * @param rpcPrefix     rpc包路径,例如"up-ecommerce-order-rpc"
     * @param webDir
     * @param webPrefix
     * @param dbName        数据库名,例如"up_ecommerce_order"
     */
    String mainPath = null;
    String srcPath = null;
    String mapDir = null;
    String mapPath = null;
    String beanName = null;
    String rpcPath = null;
    String srcRpcPath = null;
    String idType = null;
    String webPath = null;
    String srcwebPath = null;

    public void createNew(String domainDir, String mapperDir, String dbTable, String dbBean, String packagePrefix, String rpcDir, String rpcPrefix, String webDir, String webPrefix, String dbName) {
        mainPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        srcPath = mainPath + "/java/";
        mapDir = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        mapPath = (mapperDir == null ? mapDir + "/java/" : mapDir + mapperDir) + "/";
        beanName = packagePrefix + ".domain." + setPascalCase(dbBean);

        rpcPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + rpcDir + "/src/main/";
        srcRpcPath = rpcPath + "/java/";

        webPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + webDir + "/src/main/";
        srcwebPath = webPath + "/java/";

        //生成dto
        this.createDTO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".dto", dbName);
        //生成service
        this.createRPC(dbBean, srcRpcPath, rpcPrefix + ".service", rpcPrefix + ".dto", rpcPrefix + ".vo");
//
        if (!StringUtil.isEmpty(webPath) && !StringUtil.isEmpty(webPrefix)) {

//            //生成short dto
//            this.createDTO(dbTable, dbBean.replace("xp", ""), srcwebPath, webPrefix + ".dto", dbName);
            //生成controller
            this.createController(dbBean, srcwebPath, webPrefix + ".controller", rpcPrefix + ".service", rpcPrefix + ".dto", rpcPrefix + ".vo", rpcPrefix, packagePrefix);
        }
//
//        //生成vo
        this.createVO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".vo", dbName);
//        //domain
        this.createDomainBean(dbTable, dbBean, rpcPrefix + ".vo", srcPath, packagePrefix + ".domain", dbName);
//        //生成dao
        this.createDao(dbBean, srcPath, packagePrefix + ".dao");
//        //生成service的dto，vo
//
//        //生成service的impl
        this.createService(dbBean, srcPath, rpcPrefix + ".service", packagePrefix + ".impl", rpcPrefix + ".dto", rpcPrefix + ".vo", rpcPrefix, packagePrefix);
        //生成mapper
        this.createSqlMap(dbTable, dbBean, mapPath, beanName, StringUtil.str(packagePrefix, ".dao.", setPascalCase(dbBean), "Dao"), (rpcPrefix + ".vo." + setPascalCase(dbBean) + "VO"));
    }

    /**
     * @param domainDir     service模块名称,例如"up-ecommerce-order-service"
     * @param mapperDir     mapper文件存放路径,例如"resources/mybatis-mapper"
     * @param dbTable       表格名称,例如"up_bu_pay"
     * @param dbBean        同上
     * @param packagePrefix service包路径 ,例如"com.up.e.commerce.order.service"
     * @param rpcDir        rpc模块名称,例如"up-ecommerce-order-rpc"
     * @param rpcPrefix     rpc包路径,例如"up-ecommerce-order-rpc"
     * @param dbName        数据库名,例如"up_ecommerce_order"
     */
    public void createDonggu(String domainDir, String mapperDir, String dbTable, String dbBean, String packagePrefix, String rpcDir, String rpcPrefix, String dbName) {
        mainPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        srcPath = mainPath + "/java/";
        mapDir = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        mapPath = (mapperDir == null ? mapDir + "/java/" : mapDir + mapperDir) + "/";
        beanName = packagePrefix + ".domain." + setPascalCase(dbBean);

        rpcPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + rpcDir + "/src/main/";
        srcRpcPath = rpcPath + "/java/";

        //生成dto
        this.createDTO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".dto", dbName);
        //   this.createControllerDTO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".dto", dbName);
        //生成vo
        this.createVO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".vo", dbName);
        //domain
        this.createDomainBean(dbTable, dbBean, rpcPrefix + ".vo", srcPath, packagePrefix + ".domain", dbName);

        //生成dao
        this.createDao(dbBean, srcPath, packagePrefix + ".dao");
        //生成service的dto，vo
        this.createRPC(dbBean, srcRpcPath, rpcPrefix + ".service", rpcPrefix + ".dto", rpcPrefix + ".vo");
        //生成service的impl
        this.createService(dbBean, srcPath, rpcPrefix + ".service", packagePrefix + ".impl", rpcPrefix + ".dto", rpcPrefix + ".vo", rpcPrefix, packagePrefix);
        //生成mapper
        this.createSqlMap(dbTable, dbBean, mapPath, beanName, StringUtil.str(packagePrefix, ".dao.", setPascalCase(dbBean), "Dao"), "");
    }


    public void createNewControllerDto(String domainDir, String mapperDir, String dbTable, String dbBean, String packagePrefix, String rpcDir, String rpcPrefix, String dbName) {
        mainPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        srcPath = mainPath + "/java/";
        mapDir = System.getProperty("user.dir").replace('\\', '/') + "/../" + domainDir + "/src/main/";
        mapPath = (mapperDir == null ? mapDir + "/java/" : mapDir + mapperDir) + "/";
        beanName = packagePrefix + ".domain." + setPascalCase(dbBean);

        rpcPath = System.getProperty("user.dir").replace('\\', '/') + "/../" + rpcDir + "/src/main/";
        srcRpcPath = rpcPath + "/java/";

        //生成ControllerDTO
        this.createControllerDTO(dbTable, dbBean, srcRpcPath, rpcPrefix + ".dto", dbName);
    }

    public void create(String modulePath, String dbTable, String packagePrefix) {
        this.create(modulePath, null, dbTable, dbTable, packagePrefix);
    }

    public void createBean(String dbTable, String dbBean, String javaPath, String packageName) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> list = jdbc.queryForList("desc " + dbTable);
            if (list != null) {
                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
                String bean = beanPath + "/" + setPascalCase(dbBean) + ".java";
                System.out.println(bean + "开始生成。");
                FileUtil.mkDirs(beanPath);
                StringBuilder buf = new StringBuilder();
                buf.append("package ").append(packageName).append(";\n\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
                buf.append("import java.io.Serializable;\n\n");
                buf.append("/**\n").append(" * @author HRH\n").append(" * \n").append(" */\n");
                buf.append("public class ").append(setPascalCase(dbBean)).append(" implements Serializable {\n\n");
                StringBuilder fun = new StringBuilder();
                Iterator var12 = list.iterator();

                while (var12.hasNext()) {
                    Map<String, Object> row = (Map) var12.next();
                    String field = row.get("Field").toString();
                    field = setCamelCase(field);

                    String type = this.getType((String) row.get("Type"));
                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");

                    fun.append("    public ").append(type).append(" get");
                    fun.append(setFirstCharUpcase(field)).append("() {\n");
                    fun.append("        return ").append(field).append(";\n    }\n\n");
                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
                    fun.append(type).append(" ").append(field).append("){\n");
                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");
                }

                fun.append("    @Override\n");
                fun.append("    public String toString() {\n");
                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
                fun.append("    }\n\n");
                buf.append(fun).append("}");
                FileUtil.writIn(bean, buf.toString(), "UTF-8");
                System.out.println(bean + "生成完毕。");
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }


    public void createDomainBean(String dbTable, String dbBean, String rpcPath, String javaPath, String packageName, String dbName) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> messList = jdbc.queryForList("desc " + dbTable);
            List<Map<String, Object>> commentList = this.getComment(dbName, dbTable);
            List<Map<String, Object>> list = this.mergeList(messList, commentList);
            if (list != null) {
                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
                String bean = beanPath + "/" + setPascalCase(dbBean) + ".java";
                System.out.println(bean + "开始生成。");
                FileUtil.mkDirs(beanPath);
                StringBuilder buf = new StringBuilder();
                String voBean = setPascalCase(dbBean) + "VO";
                String dtoBean = setPascalCase(dbBean) + "DTO";
                System.out.println("voBean--------------------" + voBean);
                buf.append("package ").append(packageName).append(";\n\n");


                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
                buf.append("import ").append(rpcPath.replace("vo", "dto")).append(".").append(dtoBean).append(";\n\n");
                buf.append("import ").append(rpcPath).append(".").append(voBean).append(";\n\n");
                buf.append("import org.springframework.beans.BeanUtils;\n\n");
                buf.append("import java.io.Serializable;\n\n");
                buf.append("/**\n").append(" * @author AutoGenerator\n").append(" * \n").append(" */\n");
                buf.append("public class ").append(setPascalCase(dbBean)).append(" implements Serializable {\n\n");
                StringBuilder fun = new StringBuilder();
                Iterator var12 = list.iterator();

                while (var12.hasNext()) {
                    Map<String, Object> row = (Map) var12.next();
                    String field = row.get("Field").toString();
                    String comment = row.get("column_comment") == null?"":row.get("column_comment").toString();

                    field = setCamelCase(field);
                    String type = this.getType((String) row.get("Type"));
                    if (field.equals("id")) {
                        idType = type;
                    }
                    buf.append("    // ").append(comment).append("\n");
                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");
                    fun.append("    public ").append(type).append(" get");
                    fun.append(setFirstCharUpcase(field)).append("() {\n");
                    fun.append("        return ").append(field).append(";\n    }\n\n");
                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
                    fun.append(type).append(" ").append(field).append("){\n");
                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");
                }

                fun.append("    /**\n");
                fun.append("    * 查询多个id\n");
                fun.append("    */\n");
                fun.append("    private String ids;\n");
                fun.append("    public String getIds() {\n");
                fun.append("        return ids;\n");
                fun.append("    }\n\n");
                fun.append("    public void setIds(String ids) {\n");
                fun.append("        this.ids = ids;\n");
                fun.append("    }\n\n");

                //生成getVOlist方法
//                fun.append("    public static List<").append(voBean).append("> getListVO(List<").append(setPascalCase(dbBean)).append("> folderList) {\n");
//                fun.append("        ArrayList<").append(voBean).append("> voLost = new ArrayList<>();\n");
//                fun.append("         if(folderList != null && folderList.size()>0){\n");
//                fun.append("           for (").append(setPascalCase(dbBean)).append(" t: folderList) {\n");
//                fun.append("                ").append(voBean).append(" vo=toConvertBeanVO(t);\n");
//                fun.append("                voLost.add(vo);\n");
//                fun.append("            }\n");
//                fun.append("        }\n");
//                fun.append("        return voLost;\n");
//                fun.append("    }\n\n");

                //用DTO构造domain
                fun.append("\tpublic ").append(setPascalCase(dbBean)).append("(){}\n\n");
                fun.append("\tpublic ").append(setPascalCase(dbBean)).append("(").append(setPascalCase(dbBean) + "DTO " + setCamelCase(dbBean) + "DTO").append("){\n");
                fun.append("\t\tBeanUtils.copyProperties(" + setCamelCase(dbBean) + "DTO,this);\n");
                fun.append("}\n\n");

                //domain转换vo
                fun.append("    public ").append(voBean).append(" toConvertBeanVO(){\n");
                fun.append("        " + voBean).append(" voBean=new ").append(voBean).append("();\n");
                fun.append("        BeanUtils.copyProperties(this,voBean);\n");
                fun.append("      return voBean;\n");
                fun.append("    }\n\n");

                fun.append("    @Override\n");
                fun.append("    public String toString() {\n");
                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
                fun.append("    }\n\n");
                buf.append(fun).append("}");
                FileUtil.writIn(bean, buf.toString(), "UTF-8");
                System.out.println(bean + "生成完毕。");
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

//    public void createDomainBean(String dbTable, String dbBean, String rpcPath, String javaPath, String packageName, String dbName) {
//        try {
//            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
//            List<Map<String, Object>> messList = jdbc.queryForList("desc " + dbTable);
//            List<Map<String, Object>> commentList = this.getComment(dbName, dbTable);
//            List<Map<String, Object>> list = this.mergeList(messList, commentList);
//            if (list != null) {
//                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
//                String bean = beanPath + "/" + setPascalCase(dbBean) + ".java";
//                System.out.println(bean + "开始生成。");
//                FileUtil.mkDirs(beanPath);
//                StringBuilder buf = new StringBuilder();
//                String voBean = setPascalCase(dbBean) + "VO";
//                String dtoBean = setPascalCase(dbBean) + "DTO";
//                System.out.println("voBean--------------------" + voBean);
//                buf.append("package ").append(packageName).append(";\n\n");
//
//
//                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
//                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
//                buf.append("import ").append(rpcPath.replace("vo", "dto")).append(".").append(dtoBean).append(";\n\n");
//                buf.append("import ").append(rpcPath).append(".").append(voBean).append(";\n\n");
//                buf.append("import org.springframework.beans.BeanUtils;\n\n");
//                buf.append("import java.io.Serializable;\n\n");
//                buf.append("/**\n").append(" * @author HRH\n").append(" * \n").append(" */\n");
//                buf.append("public class ").append(setPascalCase(dbBean)).append(" implements Serializable {\n\n");
//                StringBuilder fun = new StringBuilder();
//                Iterator var12 = list.iterator();
//
//                while (var12.hasNext()) {
//                    Map<String, Object> row = (Map) var12.next();
//                    String field = row.get("Field").toString();
//                    String comment = row.get("column_comment") == null?"":row.get("column_comment").toString();
//
//                    field = setCamelCase(field);
//                    String type = this.getType((String) row.get("Type"));
//                    if (field.equals("id")) {
//                        idType = type;
//                    }
//                    buf.append("    // ").append(comment).append("\n");
//                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");
//                    fun.append("    public ").append(type).append(" get");
//                    fun.append(setFirstCharUpcase(field)).append("() {\n");
//                    fun.append("        return ").append(field).append(";\n    }\n\n");
//                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
//                    fun.append(type).append(" ").append(field).append("){\n");
//                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");
//                }
//
//                fun.append("    /**\n");
//                fun.append("    * 查询多个id\n");
//                fun.append("    */\n");
//                fun.append("    private String ids;\n");
//                fun.append("    public String getIds() {\n");
//                fun.append("        return ids;\n");
//                fun.append("    }\n\n");
//                fun.append("    public void setIds(String ids) {\n");
//                fun.append("        this.ids = ids;\n");
//                fun.append("    }\n\n");
//
//                //生成getVOlist方法
////                fun.append("    public static List<").append(voBean).append("> getListVO(List<").append(setPascalCase(dbBean)).append("> folderList) {\n");
////                fun.append("        ArrayList<").append(voBean).append("> voLost = new ArrayList<>();\n");
////                fun.append("         if(folderList != null && folderList.size()>0){\n");
////                fun.append("           for (").append(setPascalCase(dbBean)).append(" t: folderList) {\n");
////                fun.append("                ").append(voBean).append(" vo=toConvertBeanVO(t);\n");
////                fun.append("                voLost.add(vo);\n");
////                fun.append("            }\n");
////                fun.append("        }\n");
////                fun.append("        return voLost;\n");
////                fun.append("    }\n\n");
//
//                //用DTO构造domain
//                fun.append("\tpublic ").append(setPascalCase(dbBean)).append("(){}\n\n");
//                fun.append("\tpublic ").append(setPascalCase(dbBean)).append("(").append(setPascalCase(dbBean) + "DTO " + setCamelCase(dbBean) + "DTO").append("){\n");
//                fun.append("\t\tBeanUtils.copyProperties(" + setCamelCase(dbBean) + "DTO,this);\n");
//                fun.append("}\n\n");
//
//                //domain转换vo
//                fun.append("    public ").append(voBean).append(" toConvertBeanVO(){\n");
//                fun.append("        " + voBean).append(" voBean=new ").append(voBean).append("();\n");
//                fun.append("        BeanUtils.copyProperties(this,voBean);\n");
//                fun.append("      return voBean;\n");
//                fun.append("    }\n\n");
//
//                fun.append("    @Override\n");
//                fun.append("    public String toString() {\n");
//                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
//                fun.append("    }\n\n");
//                buf.append(fun).append("}");
//                FileUtil.writIn(bean, buf.toString(), "UTF-8");
//                System.out.println(bean + "生成完毕。");
//            }
//        } catch (Exception var15) {
//            var15.printStackTrace();
//        }
//
//    }

    /**
     * 生成DTO bean
     *
     * @param dbTable
     * @param dbBean
     * @param javaPath
     * @param packageName
     */

    public void createDTO(String dbTable, String dbBean, String javaPath, String packageName, String dbName) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> messList = jdbc.queryForList("desc " + dbTable);
            List<Map<String, Object>> commentList = this.getComment(dbName, dbTable);
            List<Map<String, Object>> list = this.mergeList(messList, commentList);
            if (list != null) {
                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
                String bean = beanPath + "/" + setPascalCase(dbBean) + "DTO.java";
                System.out.println(bean + "DTO开始生成。");
                FileUtil.mkDirs(beanPath);
                StringBuilder buf = new StringBuilder();
                buf.append("package ").append(packageName).append(";\n\n");

                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
                buf.append("import io.swagger.annotations.ApiModelProperty;\n\n");

                buf.append("import java.io.Serializable;\n\n");
                buf.append("\n\n");
                buf.append("/**\n").append(" * @author HRH\n").append(" * \n").append(" */\n");
                buf.append("public class ").append(setPascalCase(dbBean)).append("DTO  implements Serializable {\n\n");
                StringBuilder fun = new StringBuilder();
                Iterator var12 = list.iterator();

                while (var12.hasNext()) {
                    Map<String, Object> row = (Map) var12.next();
                    String field = row.get("Field").toString();
                    String comment = row.get("column_comment") == null ? "" : row.get("column_comment").toString();
                    field = setCamelCase(field);

                    String type = this.getType((String) row.get("Type"));
                    buf.append("    /** ").append(comment).append("*/\n");
                    buf.append("    @ApiModelProperty(value = \"" + comment + "\", name = \"").append(field).append("\")" + "\n");
                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");
                    fun.append("    public ").append(type).append(" get");
                    fun.append(setFirstCharUpcase(field)).append("() {\n");
                    fun.append("        return ").append(field).append(";\n    }\n\n");
                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
                    fun.append(type).append(" ").append(field).append("){\n");
                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");
                }

                fun.append("    /**\n");
                fun.append("    * 查询多个id\n");
                fun.append("    */\n");
                fun.append("    @ApiModelProperty(value = \"多个查询id拼成的字符串，用“，”分隔符\", name = \"ids\")\n");
                fun.append("    private String ids;\n");
                fun.append("    public String getIds() {\n");
                fun.append("        return ids;\n");
                fun.append("    }\n\n");
                fun.append("    public void setIds(String ids) {\n");
                fun.append("        this.ids = ids;\n");
                fun.append("    }\n\n");

                fun.append("    @Override\n");
                fun.append("    public String toString() {\n");
                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
                fun.append("    }\n\n");
                buf.append(fun).append("}");
                FileUtil.writIn(bean, buf.toString(), "UTF-8");
                System.out.println(bean + "DTO生成完毕。");
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    /**
     * 生成DTO bean
     *
     * @param dbTable
     * @param dbBean
     * @param javaPath
     * @param packageName
     */

    public void createControllerDTO(String dbTable, String dbBean, String javaPath, String packageName, String dbName) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> messList = jdbc.queryForList("desc " + dbTable);
            List<Map<String, Object>> commentList = this.getComment(dbName, dbTable);
            List<Map<String, Object>> list = this.mergeList(messList, commentList);
            if (list != null) {
                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
                String bean = beanPath + "/" + setPascalCase(dbBean).replace("Up", "") + "DTO.java";
                System.out.println(bean + "DTO开始生成。");
                FileUtil.mkDirs(beanPath);
                StringBuilder buf = new StringBuilder();
                buf.append("package ").append(packageName).append(";\n\n");

                buf.append("import java.util.List;\n");
                buf.append("import java.util.stream.Collectors;\n\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
                buf.append("import io.swagger.annotations.ApiModelProperty;\n\n");

                buf.append("import java.io.Serializable;\n\n");
                buf.append("\n\n");
                buf.append("/**\n").append(" * @author HRH\n").append(" * \n").append(" */\n");
                buf.append("public class ").append(setPascalCase(dbBean).replace("Up", "")).append("DTO implements Serializable {\n\n");
                StringBuilder fun = new StringBuilder();
                Iterator var12 = list.iterator();

                StringBuilder sb = new StringBuilder();
                StringBuilder sb1 = new StringBuilder();
                String ddlFileName = setPascalCase(dbBean).replace("Up", "") + "DTO";
                String dtoFileName = setPascalCase(dbBean).replace("Up", "") + "DTO";
                String voFileName = setPascalCase(dbBean) + "VO";

                //sb.append("    public static ").append(ddlFileName).append(" get").append(setPascalCase(dbBean).replace("Up","") + "DDL").append("(").append(dtoFileName).append(" dto){    \n");
                sb.append("    public static ").append(ddlFileName).append(" toConvertBeansDDL").append("(").append(dtoFileName).append(" dto){    \n");
                sb.append("        ").append(ddlFileName).append(" ddl = new ").append(ddlFileName).append("();\r\n");

                //sb1.append("    public static ").append(dtoFileName).append(" get").append(dtoFileName).append("(").append(ddlFileName).append(" ddl){    \n");
                sb1.append("    public static ").append(dtoFileName).append(" toConvertBeansDTO").append("(").append(voFileName).append(" ddl){    \n");
                sb1.append("        ").append(dtoFileName).append(" dto = new ").append(dtoFileName).append("();\r\n");

                while (var12.hasNext()) {
                    Map<String, Object> row = (Map) var12.next();
                    String field = row.get("Field").toString();
                    String comment = row.get("column_comment") == null ? "" : row.get("column_comment").toString();
                    field = setCamelCase(field);
                    String type = this.getType((String) row.get("Type"));
                    //buf.append("    // ").append(comment).append("\n");
                    buf.append("    @ApiModelProperty(value = \"" + comment + "\", name = \"").append(field).append("\")" + "\n");
                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");
                    fun.append("    public ").append(type).append(" get");
                    fun.append(setFirstCharUpcase(field)).append("() {\n");
                    fun.append("        return ").append(field).append(";\n    }\n\n");
                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
                    fun.append(type).append(" ").append(field).append("){\n");
                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");


                    String colname = format(field);
                    String uColname = upperFisrt(colname);
                    if (!colname.equalsIgnoreCase("createBy") && !colname.equalsIgnoreCase("modifyBy")) {
                        if (type.equalsIgnoreCase("bigint") && uColname.length() > 4 && (uColname.substring(uColname.length() - 4, uColname.length()).equalsIgnoreCase("time") || uColname.substring(uColname.length() - 4, uColname.length()).equalsIgnoreCase("date"))) {
                            // 时间字段，进行格式化
                            sb.append("        ddl.set").append(uColname).append("(dto.get").append(uColname).append("());\r\n");
                        } else {
                            sb.append("        ddl.set").append(uColname).append("(dto.get").append(uColname).append("());\r\n");
                        }
                    }


                    colname = format(field);
                    uColname = upperFisrt(colname);
                    if (!colname.equalsIgnoreCase("createTime") && !colname.equalsIgnoreCase("modifyTime")) {

                        if (type.equalsIgnoreCase("bigint") && uColname.length() > 4 && (uColname.substring(uColname.length() - 4, uColname.length()).equalsIgnoreCase("time") || uColname.substring(uColname.length() - 4, uColname.length()).equalsIgnoreCase("date"))) {
                            // 时间字段，进行格式化
                            sb1.append("        dto.set").append(uColname).append("(ddl.get").append(uColname).append("());\r\n");
                        } else if (colname.equalsIgnoreCase("createBy") || colname.equalsIgnoreCase("modifyBy")) {
                            // 时间字段，进行格式化
                            //sb1.append("        dto.set").append(uColname).append("(MemberShip.current().getName());\r\n");
                        } else {
                            sb1.append("        dto.set").append(uColname).append("(ddl.get").append(uColname).append("());\r\n");
                        }
                    }


                }

                fun.append("    /**\n");
                fun.append("    * 查询多个id\n");
                fun.append("    */\n");
                fun.append("    @ApiModelProperty(value = \"多个查询id拼成的字符串，用“，”分隔符\", name = \"ids\")\n");
                fun.append("    private String ids;\n");
                fun.append("    public String getIds() {\n");
                fun.append("        return ids;\n");
                fun.append("    }\n\n");
                fun.append("    public void setIds(String ids) {\n");
                fun.append("        this.ids = ids;\n");
                fun.append("    }\n\n");

                sb.append("        return ddl;\r\n");
                sb.append("    }\r\n\r\n");

                sb1.append("        return dto;\r\n");
                sb1.append("    }\r\n\r\n");

                fun.append(sb);
                fun.append(sb1);

                fun.append("    public static List<" + dtoFileName + "> toConvertBeansDTO(List<" + voFileName + "> " + lowerFirst(voFileName) + ") {\n");
                fun.append("        return " + lowerFirst(voFileName) + ".stream().map(" + dtoFileName + "::toConvertBeansDTO).collect(Collectors.toList());\n");
                fun.append("    }\n\n");

                fun.append("    @Override\n");
                fun.append("    public String toString() {\n");
                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
                fun.append("    }\n\n");
                buf.append(fun).append("}");
                FileUtil.writIn(bean, buf.toString(), "UTF-8");
                System.out.println(bean + "DTO生成完毕。");
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    /**
     * 功能 ：将输入字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    private String lowerFirst(String str) {
        char[] arr = str.toCharArray();
        if (arr[0] >= 'A' && arr[0] <= 'Z') {
            // arr[0] -= 'a' - 'A';
            arr[0] = (char) (arr[0] + 32);
        }
        return new String(arr);
    }

    /**
     * 功能：格式化字母串，把 _* 的字符格式化，如果 * 为字母，则改为大写，去除 _
     * 例：a_b_c 格式为 aBC
     *
     * @param str
     * @return
     */
    private String format(String str) {

        String nStr = str;
        String[] list = StringUtils.split(str, "_");
        if (list != null && list.length > 0) {
            nStr = list[0];
            if (list.length > 1) {
                for (int i = 1; i < list.length; i++) {
                    nStr += upperFisrt(list[i]);
                }
            }
        }

        return nStr;
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String upperFisrt(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    /**
     * 生成DTO bean
     *
     * @param dbTable
     * @param dbBean
     * @param javaPath
     * @param packageName
     */

    public void createVO(String dbTable, String dbBean, String javaPath, String packageName, String dbName) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> messlist = jdbc.queryForList("desc " + dbTable);
            List<Map<String, Object>> commentList = this.getComment(dbName, dbTable);
            List<Map<String, Object>> list = this.mergeList(messlist, commentList);
            if (list != null) {
                String beanPath = javaPath + packageName.replaceAll("\\.", "/");
                String bean = beanPath + "/" + setPascalCase(dbBean) + "VO.java";
                System.out.println(bean + "VO开始生成。");
                FileUtil.mkDirs(beanPath);
                StringBuilder buf = new StringBuilder();
                buf.append("package ").append(packageName).append(";\n\n");

                buf.append("import org.apache.commons.lang3.builder.ToStringBuilder;\n");
                buf.append("import org.apache.commons.lang3.builder.ToStringStyle;\n\n");
                buf.append("import io.swagger.annotations.ApiModelProperty;\n\n");

                buf.append("import java.io.Serializable;\n\n");
                buf.append("/**\n").append(" * @author HRH\n").append(" * \n").append(" */\n");
                buf.append("public class ").append(setPascalCase(dbBean)).append("VO implements Serializable {\n\n");
                StringBuilder fun = new StringBuilder();
                Iterator var12 = list.iterator();

                while (var12.hasNext()) {
                    Map<String, Object> row = (Map) var12.next();
                    String field = row.get("Field").toString();
                    String comment = row.get("column_comment")==null?"":row.get("column_comment").toString();
                    field = setCamelCase(field);

                    String type = this.getType((String) row.get("Type"));
                    buf.append("    // ").append(comment).append("\n");
                    buf.append("    @ApiModelProperty(value = \"" + comment + "\", name = \"").append(field).append("\")" + "\n");
                    buf.append("    private ").append(type).append(" ").append(field).append(";\n\n");
                    fun.append("    public ").append(type).append(" get");
                    fun.append(setFirstCharUpcase(field)).append("() {\n");
                    fun.append("        return ").append(field).append(";\n    }\n\n");
                    fun.append("    public void set").append(setFirstCharUpcase(field)).append("(");
                    fun.append(type).append(" ").append(field).append("){\n");
                    fun.append("        this.").append(field).append(" = ").append(field).append(";\n    }\n\n");
                }

                fun.append("    @Override\n");
                fun.append("    public String toString() {\n");
                fun.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);\n");
                fun.append("    }\n\n");
                buf.append(fun).append("}");
                FileUtil.writIn(bean, buf.toString(), "UTF-8");
                System.out.println(bean + "VO生成完毕。");
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    /**
     * dao 生成方法
     *
     * @param dbBean
     * @param javaPath
     * @param packageName
     */

    public void createDao(String dbBean, String javaPath, String packageName) {
        String daoPath = javaPath;

        try {
            daoPath = daoPath + packageName.replaceAll("\\.", "/");
            String bean = daoPath + "/" + setPascalCase(dbBean) + "Dao.java";
            System.out.println(bean + "开始生成。");
            FileUtil.mkDirs(daoPath);
            StringBuilder ibuf = new StringBuilder();
            ibuf.append("package ").append(packageName).append(";\n\n");
            ibuf.append("import com.learn.management.base.BaseDao;\n");

            ibuf.append("import ").append(packageName.replace("dao", "domain." + setPascalCase(dbBean))).append(";\n\n");

            ibuf.append("import org.apache.ibatis.annotations.Mapper;\n");

            ibuf.append("/**\n").append(" * @author HRH\n").append(" */\n").append("@Mapper\n");
            ibuf.append("public interface ").append(setPascalCase(dbBean)).append("Dao extends BaseDao {\n\n");
            ibuf.append("}");
            FileUtil.writIn(bean, ibuf.toString(), "UTF-8");
            System.out.println(bean + "生成完毕。");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    /**
     * @param dbBean
     * @param javaPath
     * @param packageName
     * @param rpcDtoPath
     * @param rpcVoPath
     */

    public void createRPC(String dbBean, String javaPath, String packageName, String rpcDtoPath, String rpcVoPath) {
        String daoPath = javaPath;

        try {
            daoPath = daoPath + packageName.replaceAll("\\.", "/");
            String bean = daoPath + "/I" + setPascalCase(dbBean) + "Service.java";
            System.out.println(bean + "RPC开始生成。");
            FileUtil.mkDirs(daoPath);
            String voBean = setPascalCase(dbBean) + "VO";
            String dtoBean = setPascalCase(dbBean) + "DTO";
            StringBuilder ibuf = new StringBuilder();
            ibuf.append("package ").append(packageName).append(";\n\n");
            //  ibuf.append("import com.donggu.common.base.BaseService;\n");
            ibuf.append("import ").append(rpcDtoPath).append(".").append(dtoBean).append(";\n");
            ibuf.append("import ").append(rpcVoPath).append(".").append(voBean).append(";\n");
            ibuf.append("import com.github.pagehelper.PageInfo;").append("\n");
            ibuf.append("import java.util.Map;").append("\n");

            ibuf.append("/**\n").append(" * @author HRH\n").append(" */\n");
            //ibuf.append("public interface I").append(setPascalCase(dbBean)).append("Service extends BaseService<").append(dtoBean).append(",").append(voBean).append(",").append(idType).append("> {\n\n");
            ibuf.append("public interface I").append(setPascalCase(dbBean)).append("Service").append(" {\n\n");
            ibuf.append("\t int deleteByDTO(").append(dtoBean).append(" dto);").append("\n\n");
            ibuf.append("\t int deleteById(Long id);").append("\n\n");
            ibuf.append("\t ").append(voBean).append(" selectByDTO(").append(dtoBean).append(" dto);").append("\n\n");
            ibuf.append("\t ").append(voBean).append("  selectById(Long id);\n\n");
            ibuf.append("\t ").append(voBean).append(" modify(").append(dtoBean).append(" dto);").append("\n\n");
            ibuf.append("\t PageInfo<").append(voBean).append(" > listPage(int pageNum, int pageSize, String orderBy, Map map);").append("\n\n");

            ibuf.append("}");
            FileUtil.writIn(bean, ibuf.toString(), "UTF-8");
            System.out.println(bean + "生成完毕" +
                    "。");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    /**
     * rpc 实现类
     *
     * @param dbBean
     * @param javaPath
     * @param rpcPath
     * @param packageName
     */
    public void createServiceImpl(String dbBean, String javaPath, String rpcPath, String packageName, String rpcDtoPath, String rpcVoPath, String rpcPrefix, String packagePrefix) {
        String daoPath = javaPath;

        try {
            daoPath = daoPath + packageName.replaceAll("\\.", "/");
            String bean = daoPath + "/" + setPascalCase(dbBean) + "ServiceImpl.java";
            String rpcBean = "I" + setPascalCase(dbBean) + "Service";
            String voBean = setPascalCase(dbBean) + "VO";
            String dtoBean = setPascalCase(dbBean) + "DTO";
            String daoBean = setPascalCase(dbBean) + "Dao";
            String domainBean = setPascalCase(dbBean);
            System.out.println(bean + "Service开始生成。");
            FileUtil.mkDirs(daoPath);
            StringBuilder ibuf = new StringBuilder();
            ibuf.append("package ").append(packageName).append(";\n\n");
            ibuf.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            ibuf.append("import ").append(packagePrefix).append(".dao.").append(daoBean).append(";\n");
            ibuf.append("import ").append(packagePrefix).append(".domain.").append(domainBean).append(";\n");
            ibuf.append("import ").append(rpcPrefix).append(".vo.").append(voBean).append(";\n");
            ibuf.append("import ").append(rpcPrefix).append(".dto.").append(dtoBean).append(";\n");
            ibuf.append("import ").append(rpcPath).append(".").append(rpcBean).append(";\n");
            ibuf.append("import java.util.List;\n");
            ibuf.append("import java.util.ArrayList;\n");
            ibuf.append("import java.util.Map;\n");

            ibuf.append("/**\n").append(" * @author HRH\n").append(" */\n");
            ibuf.append("@Service\n");
            ibuf.append("public class ").append(setPascalCase(domainBean)).append("ServiceImpl").append(" implements ").append(rpcBean).append("{\n\n");
            ibuf.append("\t@Autowired\n");
            ibuf.append("\t" + daoBean + " " + setFirstLowerCase(daoBean) + ";\n\n");

           /* ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int deleteByDTO(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
            ibuf.append("\t\t//逻辑删除操作\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setDeleted(1);\n");
            ibuf.append("\t\t" + "//等待用户体系介入获取编辑用户userId\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setModifyTime(System.currentTimeMillis());\n");
            ibuf.append("\t\t" + "return " + setFirstLowerCase(daoBean) + ".update(new " + setCamelCase(domainBean) + "(" + setFirstLowerCase(dtoBean) + "));\n");
            ibuf.append(" \t}\n\n");

            ibuf.append("\t@Override\n");

            ibuf.append("\tpublic int deleteById("+idType+" id) {\n");
            ibuf.append("\t\t"+setFirstCharUpcase(dtoBean)+" "+setFirstLowerCase(dtoBean)+" = new "+setFirstCharUpcase(dtoBean)+"();\n");
            ibuf.append("\t\t"+setFirstLowerCase(dtoBean)+".setId(id);\n");
            ibuf.append("\t\t" + "return deleteByDTO("+setFirstLowerCase(dtoBean)+");\n");
            ibuf.append(" \t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic " + setFirstCharUpcase(voBean) + " selectByDTO(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
            ibuf.append("\t\t" + " return " + setFirstLowerCase(daoBean) + ".select(new " + setFirstCharUpcase(domainBean) + "(" + setFirstLowerCase(dtoBean) + ")).toConvertBeanVO();\n");

            ibuf.append("\t}\n\n");


            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic " + setFirstCharUpcase(voBean) + " selectById("+idType+" id) {\n");
            ibuf.append("\t\t" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + " = new " + setFirstCharUpcase(dtoBean) + "();\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setId(id);\n");
            ibuf.append("\t\t" + "return selectByDTO(" + setFirstLowerCase(dtoBean) + ");\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> selectAll() {\n");
            ibuf.append("\t\treturn null;\n");
            ibuf.append(" \t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> findList(Map map) {\n");
            ibuf.append("\t\tmap.put(\"deleted\", 0);\n");
//            ibuf.append("\t\tList<" + setFirstCharUpcase(voBean) + "> ddlList = (List<" + setFirstCharUpcase(voBean) + ">)" + setFirstLowerCase(daoBean) + ".list(map) ;\n");
            ibuf.append("\t\tList<" + setFirstCharUpcase(domainBean) + "> domainList = " + setFirstLowerCase(daoBean) + ".list(map) ;\n");
            ibuf.append("\t\tList<" + setFirstCharUpcase(voBean) + "> voList = new ArrayList<>();\n");
            ibuf.append("\t\tfor (" + setFirstCharUpcase(domainBean) + " " + setFirstLowerCase(domainBean) + ":domainList" + ") {\n");
            ibuf.append("\t\t\tvoList.add(" + setFirstLowerCase(domainBean) + ".toConvertBeanVO());\n");
            ibuf.append("\t\t}\n");
            ibuf.append("\t\treturn voList;\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int totalCount(Map map) {\n");
            ibuf.append("\t\treturn " + setFirstLowerCase(daoBean) + ".count(map);");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic "+idType+" insert(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
            ibuf.append("\t\t"+setFirstCharUpcase(domainBean)+" "+setFirstLowerCase(domainBean)+" = new "+setFirstCharUpcase(domainBean)+"("+setFirstLowerCase(dtoBean)+");\n");
            ibuf.append("\t\t"+setFirstLowerCase(daoBean) + ".insert("+setFirstLowerCase(domainBean)+");\n");
            ibuf.append("\t\treturn "+setFirstLowerCase(domainBean)+".getId();\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> findList(List list) {\n");
            ibuf.append("\t\treturn null;");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int totalCount(List list) {\n");
            ibuf.append("\t\treturn 0;");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int update(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
            ibuf.append("\t\t//等待用户体系介入获取编辑用户userId\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setModifyTime(System.currentTimeMillis());\n");
            ibuf.append("\t\treturn " + setFirstLowerCase(daoBean) + ".update(new " + setFirstCharUpcase(domainBean) + "(" + setFirstLowerCase(dtoBean) + "));\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic "+idType+" insertPart(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
            ibuf.append("\t\t"+setFirstCharUpcase(domainBean)+" "+setFirstLowerCase(domainBean)+" = new "+setFirstCharUpcase(domainBean)+"("+setFirstLowerCase(dtoBean)+");\n");
            ibuf.append("\t\t"+setFirstLowerCase(daoBean) + ".insertPart("+setFirstLowerCase(domainBean)+");\n");
            ibuf.append("\t\treturn "+setFirstLowerCase(domainBean)+".getId();\n");
            ibuf.append("\t}\n\n");*/

            ibuf.append("}");
            FileUtil.writIn(bean, ibuf.toString(), "UTF-8");
            System.out.println(bean + "生成完毕。");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    /**
     * rpc 实现类
     *
     * @param dbBean
     * @param javaPath
     * @param packageName
     */
    public void createController(String dbBean, String javaPath, String packageName, String rpcServicePath, String rpcDtoPath, String rpcVoPath, String rpcPrefix, String packagePrefix) {
        String daoPath = javaPath;

        try {
            String domainBean = setPascalCase(dbBean);

            String className = domainBean;
//            String className = domainBean.substring(4, domainBean.length());
//            if (domainBean.contains("XpSys")) {
//                className = domainBean.substring(5, domainBean.length());
//            }

            daoPath = daoPath + packageName.replaceAll("\\.", "/");
            String bean = daoPath + "/" + className + "Controller.java";
            String rpcBean = "I" + setPascalCase(dbBean) + "Service";
            String voBean = setPascalCase(dbBean) + "VO";
            String dtoBean = setPascalCase(dbBean) + "DTO";
            //公司定制字段，不具备通用性
            String dtoBeanShort = setPascalCase(dbBean).replace("Xp", "") + "DTO";
            String daoBean = setPascalCase(dbBean) + "Dao";
            System.out.println(bean + "Controller开始生成。");
            FileUtil.mkDirs(daoPath);
            StringBuilder ibuf = new StringBuilder();
            ibuf.append("package ").append(packageName).append(";\n\n");
//            ibuf.append("import com.alibaba.dubbo.config.annotation.Reference;\r\n");
            ibuf.append("import com.github.pagehelper.PageInfo;\r\n");
            ibuf.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
            ibuf.append("import io.swagger.annotations.Api;\r\n");
            ibuf.append("import io.swagger.annotations.ApiOperation;\r\n");
            ibuf.append("import io.swagger.annotations.ApiParam;\r\n");
            ibuf.append("import org.slf4j.Logger;\r\n");
            ibuf.append("import org.slf4j.LoggerFactory;\r\n");
            ibuf.append("import org.springframework.beans.BeanUtils;\r\n");
            ibuf.append("import org.springframework.web.bind.annotation.*;\r\n");
            ibuf.append("import com.learn.management.constant.OrderEnum;\r\n");
            ibuf.append("import com.learn.management.util.DateUtil;\r\n");

            ibuf.append("import ").append(rpcVoPath + ".").append(voBean).append(";\n");
            ibuf.append("import ").append(rpcDtoPath + ".").append(dtoBean).append(";\n");
            ibuf.append("import ").append(rpcServicePath).append(".").append(rpcBean).append(";\n");
            String webDTO = rpcPrefix.replace("service.rpc", "web");
            ibuf.append("import ").append(webDTO).append(".dto.").append(dtoBean.replace("Xp", "")).append(";\n");

            ibuf.append("import java.util.List;\n");
            ibuf.append("import java.util.ArrayList;\n");
            ibuf.append("import java.util.Map;\n");
            ibuf.append("import java.util.HashMap;\n");


            ibuf.append("/**\n").append(" * @author HRH\n").append(" */\n");
            ibuf.append("@Api(value = \""+className+"\" , description = \""+className+"Controller\" , tags = \"").append(className).append("Controller").append("\")\n");
            ibuf.append("@RestController\n");
            ibuf.append("@RequestMapping(\"/" + className+"Controller").append("\")\n");

            ibuf.append("public class ").append(className).append("Controller").append("{\n\n");
            ibuf.append("\tprivate final Logger logger = LoggerFactory.getLogger(").append(className).append("Controller").append(".class);\n\n");
            ibuf.append("\t@Autowired\n");
            ibuf.append("\tprivate ").append(rpcBean).append(" ").append(setFirstLowerCase(rpcBean)).append(";\n\n");

            //add新增记录的方法
            ibuf.append("\t@ApiOperation(value = \"新增记录\", notes = \"新增记录\")\n");
            ibuf.append("\t@PostMapping\n");
            ibuf.append("\tpublic ").append(voBean).append(" add(@RequestBody ").append(setFirstCharUpcase(dtoBeanShort)).append(" dto){\n");
            ibuf.append("\t\t").append("//todo 以下需要开发人员手动检查新增字段").append("\n");
            ibuf.append("\t\t").append(dtoBean).append(" ddl = new ").append(dtoBean).append("();\n");
            ibuf.append("\t\t").append("BeanUtils.copyProperties(dto, ddl);\n");
            ibuf.append("\t\t").append("//ddl.setCreateBy(UserDetailHelper.getUserId().toString());\n");
            ibuf.append("\t\t").append("//ddl.setCreateTime(DateUtil.nowTime());\n");
            ibuf.append("\t\t").append("try {\n");
            ibuf.append("\t\t\t").append("return ").append(setFirstLowerCase(rpcBean)).append(".modify(ddl);\n");
            ibuf.append("\t\t").append("} catch (Exception ex) {\n");
            ibuf.append("\t\t\t").append("logger.info(ex.getMessage());\n");
            ibuf.append("\t\t\t").append("ex.printStackTrace();\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("return null;\n");
            ibuf.append("\t}\n\n");

            //update编辑记录的方法
            ibuf.append("\t@ApiOperation(value = \"编辑记录\", notes = \"编辑记录\")\n");
            ibuf.append("\t@PutMapping\n");
            ibuf.append("\tpublic ").append(voBean).append(" update(@RequestBody ").append(setFirstCharUpcase(dtoBeanShort)).append(" dto){\n");
            ibuf.append("\t\t").append("//todo 以下需要开发人员手动检查编辑字段").append("\n");
            ibuf.append("\t\t").append("if (dto.getId() == null || dto.getId() <= 0) {\n");
            ibuf.append("\t\t\t").append("throw new IllegalArgumentException(\"请求参数错误,id不能为空\");\n");
            ibuf.append("\t\t}\n");
            ibuf.append("\t\t").append(dtoBean).append(" ddl = new ").append(dtoBean).append("();\n");
            ibuf.append("\t\t").append("BeanUtils.copyProperties(dto, ddl);\n");
            ibuf.append("\t\t").append("//ddl.setModifyBy(UserDetailHelper.getUserId().toString());\n");
            ibuf.append("\t\t").append("//ddl.setModifyTime(DateUtil.nowTime());\n");
            ibuf.append("\t\t").append("try {\n");
            ibuf.append("\t\t\t").append("return ").append(setFirstLowerCase(rpcBean)).append(".modify(ddl);\n");
            ibuf.append("\t\t").append("} catch (Exception ex) {\n");
            ibuf.append("\t\t\t").append("logger.info(ex.getMessage());\n");
            ibuf.append("\t\t\t").append("ex.printStackTrace();\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("return null;\n");
            ibuf.append("\t}\n\n");

            //delete删除记录的方法
            ibuf.append("\t@ApiOperation(value = \"删除记录\", notes = \"删除记录\")\n");
            ibuf.append("\t@DeleteMapping(\"/{id}\")\n");
            ibuf.append("\tpublic void delete(@ApiParam(value = \"记录ID\", required = true) @PathVariable Long id) {\n");
            ibuf.append("\t\t").append("if (id == null) {\n");
            ibuf.append("\t\t\t").append("throw new IllegalArgumentException(\"请求参数错误\");\n");
            ibuf.append("\t\t}\n");
            ibuf.append("\t\t").append(dtoBean).append(" ddl = new ").append(dtoBean).append("();\n");
            ibuf.append("\t\t").append("//ddl.setModifyBy(UserDetailHelper.getUserId().toString());\n");
            ibuf.append("\t\t").append("//ddl.setModifyTime(DateUtil.nowTime());\n");
            ibuf.append("\t\t").append("ddl.setId(id);\n");
            ibuf.append("\t\t").append("try {\n");
            ibuf.append("\t\t\t").append("").append(setFirstLowerCase(rpcBean)).append(".deleteByDTO(ddl);\n");
            ibuf.append("\t\t").append("} catch (Exception ex) {\n");
            ibuf.append("\t\t\t").append("logger.info(ex.getMessage());\n");
            ibuf.append("\t\t\t").append("ex.printStackTrace();\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t}\n\n");


            //listPage查询记录的方法
            ibuf.append("\t@ApiOperation(value = \"查询记录\", notes = \"查询记录\")\n");
            ibuf.append("\t@GetMapping(\"/{pageNum}/{pageSize}\")\n");
            ibuf.append("\tpublic PageInfo<").append(voBean).append(">").append(" listPage(@ApiParam(value = \"分页索引\", required = true, defaultValue = \"1\") @PathVariable int pageNum,\n");
            ibuf.append("\t                                                @ApiParam(value = \"分页大小\", required = true, defaultValue = \"20\") @PathVariable int pageSize,\n");
            ibuf.append("\t                                                @ApiParam(value = \"排序字段[createTime,id,deleted]，索引为1开始，不填写默认按照创建时间降序排序\", required = false) @RequestParam(value = \"sort\", required = false, defaultValue = \"-1\") Integer sort,\n");
            ibuf.append("\t                                                @ApiParam(value = \"记录ID\", required = false) @RequestParam(value = \"id\", required = false) Long id) {\n");
            ibuf.append("\t\t").append("if (pageSize > 50) {\n");
            ibuf.append("\t\t\t").append("throw new IllegalArgumentException(\"请求参数错误,pageSize最大50\");\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("//排序处理\n");
            ibuf.append("\t\t").append("if (sort == null || sort == 0) {\n");
            ibuf.append("\t\t\t").append("sort = 1;\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("//该字符串要参考数据库实际字段名称\n");
            ibuf.append("\t\t").append("String orderByStr = \"create_time,id,deleted\";\n");
            ibuf.append("\t\t").append("String[] orderByList = orderByStr.split(\",\");\n");
            ibuf.append("\t\t").append("if (orderByList.length < (Math.abs(sort))) {\n");
            ibuf.append("\t\t\t").append("throw new IllegalArgumentException(\"请求参数错误,sort参数错误\");\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("String orderBy = orderByList[Math.abs(sort) - 1];\n");
            ibuf.append("\t\t").append("if (sort > 0) {\n");
            ibuf.append("\t\t\t").append("orderBy = orderBy + OrderEnum.ASC;\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("if (sort < 0) {\n");
            ibuf.append("\t\t\t").append("orderBy = orderBy + OrderEnum.DESC;\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("try {\n");
            ibuf.append("\t\t\t").append("Map<String,Object> map=new HashMap<String,Object>()").append(";\n");
            ibuf.append("\t\t\t").append("//todo 以下需要开发人员手动改动查询字段").append("\n");
            ibuf.append("\t\t\t").append("if (id != null){").append("\n");
            ibuf.append("\t\t\t\t").append("map.put(\"id\", id)").append(";\n");
            ibuf.append("\t\t\t").append("}").append("\n");
            ibuf.append("\t\t\t").append("map.put(\"deleted\", 0)").append(";\n");
            ibuf.append("\t\t\t").append("return ").append(setFirstLowerCase(rpcBean)).append(".listPage(pageNum, pageSize, orderBy, map);\n");
            ibuf.append("\t\t").append("} catch (Exception ex) {\n");
            ibuf.append("\t\t\t").append("logger.info(ex.getMessage());\n");
            ibuf.append("\t\t\t").append("ex.printStackTrace();\n");
            ibuf.append("\t\t").append("}\n");
            ibuf.append("\t\t").append("return null;\n");
            ibuf.append("\t").append("}\n");
            ibuf.append("}");
            FileUtil.writIn(bean, ibuf.toString(), "UTF-8");
            System.out.println(bean + "生成完毕。");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    /**
     * rpc 实现类
     *
     * @param dbBean
     * @param javaPath
     * @param rpcPath
     * @param packageName
     */
    public void createService(String dbBean, String javaPath, String rpcPath, String packageName, String rpcDtoPath, String rpcVoPath, String rpcPrefix, String packagePrefix) {
        String daoPath = javaPath;

        try {
            daoPath = daoPath + packageName.replaceAll("\\.", "/");
            String bean = daoPath + "/" + setPascalCase(dbBean) + "ServiceImpl.java";
            String rpcBean = "I" + setPascalCase(dbBean) + "Service";
            String voBean = setPascalCase(dbBean) + "VO";
            String dtoBean = setPascalCase(dbBean) + "DTO";
            String daoBean = setPascalCase(dbBean) + "Dao";
            String domainBean = setPascalCase(dbBean);
            System.out.println(bean + "Service开始生成。");
            FileUtil.mkDirs(daoPath);
            StringBuilder ibuf = new StringBuilder();
            ibuf.append("package ").append(packageName).append(";\n\n");
            ibuf.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            ibuf.append("import org.springframework.stereotype.Service;\n");
            ibuf.append("import ").append(packagePrefix).append(".dao.").append(daoBean).append(";\n");
            ibuf.append("import ").append(packagePrefix).append(".domain.").append(domainBean).append(";\n");
            ibuf.append("import ").append(rpcPrefix).append(".vo.").append(voBean).append(";\n");
            ibuf.append("import ").append(rpcPrefix).append(".dto.").append(dtoBean).append(";\n");
            ibuf.append("import ").append(rpcPath).append(".").append(rpcBean).append(";\n");
            ibuf.append("import com.github.pagehelper.PageHelper;\n");
            ibuf.append("import com.github.pagehelper.PageInfo;\n");
            ibuf.append("import java.util.HashMap;\n");
            ibuf.append("import java.util.List;\n");
            ibuf.append("import java.util.Map;\n");
            ibuf.append("import com.learn.management.util.BeanMapper;\n");


            ibuf.append("/**\n").append(" * @author HRH\n").append(" */\n");
            ibuf.append("@Service\n");
            ibuf.append("public class ").append(setPascalCase(domainBean)).append("ServiceImpl").append(" implements ").append(rpcBean).append("{\n\n");
            ibuf.append("\t@Autowired\n");
            ibuf.append("\t" + daoBean + " " + setFirstLowerCase(daoBean) + ";\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int deleteByDTO(" + setFirstCharUpcase(dtoBean) + " dto) {\n");
            ibuf.append("\t\t//逻辑删除操作\n");
            ibuf.append("\t\tdto.setDeleted(1);\n");
            ibuf.append("\t\t" + "return " + setFirstLowerCase(daoBean) + ".update(dto);\n");
            ibuf.append(" \t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic int deleteById(Long id) {\n");
            ibuf.append("\t\t" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + " = new " + setFirstCharUpcase(dtoBean) + "();\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setId(id);\n");
            ibuf.append("\t\t" + "return deleteByDTO(" + setFirstLowerCase(dtoBean) + ");\n");
            ibuf.append(" \t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic " + setFirstCharUpcase(voBean) + " selectByDTO(" + setFirstCharUpcase(dtoBean) + " dto) {\n");
            ibuf.append("\t\t" + setFirstCharUpcase(voBean) + " vo = (" + setFirstCharUpcase(voBean) + ")" + setFirstLowerCase(daoBean) + ".select(BeanMapper.toMap(dto));\n");
            ibuf.append("\t\treturn vo;\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic " + setFirstCharUpcase(voBean) + " selectById(Long id) {\n");
            ibuf.append("\t\t" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + " = new " + setFirstCharUpcase(dtoBean) + "();\n");
            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setId(id);\n");
            ibuf.append("\t\t" + "return selectByDTO(" + setFirstLowerCase(dtoBean) + ");\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic " + setFirstCharUpcase(voBean) + " modify(" + setFirstCharUpcase(dtoBean) + " dto) {\n");
            ibuf.append("\t\tif (dto.getId() == null || dto.getId() <= 0) {\n");
            ibuf.append("\t\t\t").append(domainBean).append(" domain = new ").append(domainBean).append("(dto);\n");
            ibuf.append("\t\t\t").append(setFirstLowerCase(daoBean)).append(".insertPart(domain);\n");
            ibuf.append("\t\t\treturn domain.toConvertBeanVO();\n");
            ibuf.append("\t\t} else {\n");
            ibuf.append("\t\t\tMap map=new HashMap<>();\n");
            ibuf.append("\t\t\tmap.put(\"id\", dto.getId());\n");
            ibuf.append("\t\t\t").append(setFirstCharUpcase(voBean)).append(" vo = (" + setFirstCharUpcase(voBean) + ")" + setFirstLowerCase(daoBean) + ".select(map);\n");
            ibuf.append("\t\t\tif(vo == null){\n");
            ibuf.append("\t\t\t\tthrow new IllegalArgumentException(\"请求参数异常\");\n");
            ibuf.append("\t\t\t}\n");
            ibuf.append("\t\t\tBeanMapper.copy(dto, vo);\n");
            ibuf.append("\t\t\t").append(setFirstLowerCase(daoBean) + ".update(vo);\n");
            ibuf.append("\t\t\t").append("return vo;\n");
            ibuf.append("\t\t}\n");
            ibuf.append("\t}\n\n");

            ibuf.append("\t@Override\n");
            ibuf.append("\tpublic PageInfo<" + setFirstCharUpcase(voBean) + "> listPage(int pageNum, int pageSize, String orderBy, Map map) {\n");
            ibuf.append("\t\tPageHelper.startPage(pageNum, pageSize, orderBy);\n");
            ibuf.append("\t\tList<" + setFirstCharUpcase(voBean) + "> " + setFirstLowerCase(voBean) + "List = " + setFirstLowerCase(daoBean) + ".list(map);\n");
            ibuf.append("\t\treturn new PageInfo<>(" + setFirstLowerCase(voBean) + "List)" + ";\n");
            ibuf.append("\t}\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> selectAll() {\n");
//            ibuf.append("\t\treturn null;\n");
//            ibuf.append(" \t}\n\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> findList(Map map) {\n");
//            ibuf.append("\t\tmap.put(\"deleted\", 0);\n");
////            ibuf.append("\t\tList<" + setFirstCharUpcase(voBean) + "> ddlList = (List<" + setFirstCharUpcase(voBean) + ">)" + setFirstLowerCase(daoBean) + ".list(map) ;\n");
//            ibuf.append("\t\tList<" + setFirstCharUpcase(domainBean) + "> domainList = " + setFirstLowerCase(daoBean) + ".list(map) ;\n");
//            ibuf.append("\t\tList<" + setFirstCharUpcase(voBean) + "> voList = new ArrayList<>();\n");
//            ibuf.append("\t\tfor (" + setFirstCharUpcase(domainBean) + " " + setFirstLowerCase(domainBean) + ":domainList" + ") {\n");
//            ibuf.append("\t\t\tvoList.add(" + setFirstLowerCase(domainBean) + ".toConvertBeanVO());\n");
//            ibuf.append("\t\t}\n");
//            ibuf.append("\t\treturn voList;\n");
//            ibuf.append("\t}\n\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic int totalCount(Map map) {\n");
//            ibuf.append("\t\treturn " + setFirstLowerCase(daoBean) + ".count(map);");
//            ibuf.append("\t}\n\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic "+idType+" insert(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
//            ibuf.append("\t\t"+setFirstCharUpcase(domainBean)+" "+setFirstLowerCase(domainBean)+" = new "+setFirstCharUpcase(domainBean)+"("+setFirstLowerCase(dtoBean)+");\n");
//            ibuf.append("\t\t"+setFirstLowerCase(daoBean) + ".insert("+setFirstLowerCase(domainBean)+");\n");
//            ibuf.append("\t\treturn "+setFirstLowerCase(domainBean)+".getId();\n");
//            ibuf.append("\t}\n\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic List<" + setFirstCharUpcase(voBean) + "> findList(List list) {\n");
//            ibuf.append("\t\treturn null;");
//            ibuf.append("\t}\n\n");
//
//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic int totalCount(List list) {\n");
//            ibuf.append("\t\treturn 0;");
//            ibuf.append("\t}\n\n");

//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic int update(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
//            ibuf.append("\t\t//等待用户体系介入获取编辑用户userId\n");
//            ibuf.append("\t\t" + setFirstLowerCase(dtoBean) + ".setModifyTime(System.currentTimeMillis());\n");
//            ibuf.append("\t\treturn " + setFirstLowerCase(daoBean) + ".update(new " + setFirstCharUpcase(domainBean) + "(" + setFirstLowerCase(dtoBean) + "));\n");
//            ibuf.append("\t}\n\n");
//
//            ibuf.append("\t@Override\n");
//            ibuf.append("\tpublic "+idType+" insertPart(" + setFirstCharUpcase(dtoBean) + " " + setFirstLowerCase(dtoBean) + ") {\n");
//            ibuf.append("\t\t"+setFirstCharUpcase(domainBean)+" "+setFirstLowerCase(domainBean)+" = new "+setFirstCharUpcase(domainBean)+"("+setFirstLowerCase(dtoBean)+");\n");
//            ibuf.append("\t\t"+setFirstLowerCase(daoBean) + ".insertPart("+setFirstLowerCase(domainBean)+");\n");
//            ibuf.append("\t\treturn "+setFirstLowerCase(domainBean)+".getId();\n");
//            ibuf.append("\t}\n\n");

            ibuf.append("}");
            FileUtil.writIn(bean, ibuf.toString(), "UTF-8");
            System.out.println(bean + "生成完毕。");
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void createSqlMap(String dbTable, String dbBean, String mapPath, String beanName, String daoName, String rpcPrefix) {
        try {
            JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
            List<Map<String, Object>> list = jdbc.queryForList("desc " + dbTable);
            if (list != null) {

                String xml = mapPath + setPascalCase(dbBean) + "Dao.xml";
                System.out.println(xml + "开始生成。");
                String keyName = list.get(0).get("Field").toString();
                String camelKey = setCamelCase(keyName);
                StringBuilder buf = new StringBuilder();
                buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                buf.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n\n");
                buf.append("<mapper namespace=\"").append(daoName).append("\">\n\n");
                StringBuffer baseColumn = new StringBuffer();
                baseColumn.append(keyName).append(",");
                buf.append("    <sql id=\"dynamicWhere\">\n");
                buf.append("        <where>\n");
                //xml 文件生成where

                buf.append("            <if test=\"_parameter.containsKey('").append(camelKey).append("s') and ").append(camelKey).append("s != null and ").append(camelKey).append("s != ''\">\n");
                buf.append("                and ").append(keyName).append(" in (${").append(camelKey).append("s})\n");
                buf.append("            </if>\n");
                buf.append("            <if test=\"_parameter.containsKey('").append(camelKey).append("') and ").append(camelKey).append(" != null and ").append(camelKey).append(" != ''\">\n");
                buf.append("                and ").append(keyName).append(" = #{").append(camelKey).append("}\n");
                buf.append("            </if>\n");
                int ii = 0;
                for (Iterator var15 = list.iterator(); var15.hasNext(); ++ii) {
                    Map<String, Object> row = (Map) var15.next();
                    if (ii != 0) {
                        String field = row.get("Field").toString();
                        String camelField = setCamelCase(field);
                        String type = this.getType((String) row.get("Type"));
//                        if (camelField.equals("id")) {
//                            buf.append("            <if test=\"_parameter.containsKey('").append(camelKey).append("s') and ").append(camelKey).append("s != null and ").append(camelKey).append("s != ''\">\n");
//                            buf.append("                and ").append(keyName).append(" in (${").append(camelKey).append("s})\n");
//                            buf.append("            </if>\n");
//                        }
                        if (camelField.contains("id")) {
                            buf.append("            <if test=\"_parameter.containsKey('").append(camelField).append("') and ").append(camelField).append(" != null and ").append(camelField).append("s != ''\">\n");
                            buf.append("                and ").append(field).append(" in (${").append(camelField).append("s})\n");
                            buf.append("            </if>\n");
                        }
                        if (type.equals("Integer") || type.equals("Long") || type.equals("Double") || type.equals("Float")) {
                            if (camelField.contains("deleted")) {
                                buf.append("            <if test=\"_parameter.containsKey('").append(camelField).append("') and ").append(camelField).append(" != null and ").append(camelField).append(" >= 0\">\n");
                            } else {
                                buf.append("            <if test=\"_parameter.containsKey('").append(camelField).append("') and ").append(camelField).append(" != null and ").append(camelField).append(" > 0\">\n");
                            }
                            buf.append("                and ").append(field).append(" = #{").append(camelField).append("}\n");
                            buf.append("            </if>\n");
                        }
                        if (type.equals("String")) {
                            buf.append("            <if test=\"_parameter.containsKey('").append(camelField).append("') and ").append(camelField).append(" != null and ").append(camelField).append(" !=''\">\n");
                            buf.append("                and ").append(field).append(" like concat(#{").append(camelField).append("},'%')\n");
                            buf.append("            </if>\n");
                        }
                        baseColumn.append(field).append(",");
                    }
                }
                buf.append("        </where>\n");
                buf.append("    </sql>\n\n");

                buf.append("    <sql id=\"baseColumn\">\n");
                buf.append("        " + baseColumn.substring(0, baseColumn.length() - 1) + "\n");
                buf.append("    </sql>\n\n");

                buf.append("    <select id=\"select\" resultType=\"").append(rpcPrefix).append("\">\n");
                buf.append("        select  <include refid=\"baseColumn\"/>   from ").append(dbTable).append("\n");
                buf.append("            <include refid=\"dynamicWhere\"/>\n");
                buf.append("    </select>\n\n");
                buf.append("    <select id=\"count\" resultType=\"int\">\n");
                buf.append("        select count(0) from ").append(dbTable).append("\n");
                buf.append("        <include refid=\"dynamicWhere\" />\n");
                buf.append("    </select>\n\n");
                buf.append("    <select id=\"list\" resultType=\"").append(rpcPrefix).append("\">\n");
                buf.append("        select  <include refid=\"baseColumn\"/>  from ").append(dbTable).append("\n");
                buf.append("        <include refid=\"dynamicWhere\" />\n");
                buf.append("        <if test=\"_parameter.containsKey('orderBy') and orderBy!=null\">\n");
                buf.append("            order by ${orderBy}\n");
                buf.append("        </if>\n");
                //     buf.append("        limit #{offset},#{count}\n");
                buf.append("    </select>\n\n");
                buf.append("    <insert id=\"insert\" useGeneratedKeys=\"true\" keyProperty=\"id\" parameterType=\"").append(beanName).append("\">\n");
                buf.append("        insert into ").append(dbTable).append(" (");

                StringBuilder insert = new StringBuilder();
                StringBuilder update = new StringBuilder();
                StringBuilder insertPart = new StringBuilder();
                int i = 0;

                for (Iterator var15 = list.iterator(); var15.hasNext(); ++i) {
                    Map<String, Object> row = (Map) var15.next();
                    if (i != 0) {
                        String field = row.get("Field").toString();
                        String camelField = setCamelCase(field);
                        String type = this.getType((String) row.get("Type"));
                        if (i != 1) {
                            buf.append(",");
                            insert.append(",");
                        }

                        buf.append(field);
                        insert.append("#{").append(camelField).append("}");
//                        if ("String".equals(type)) {
                        update.append("            <if test=\"").append(camelField).append(" != null\">\n");
//                        } else {
//                            if(camelField.equals("deleted")){
//                                update.append("            <if test=\"").append(camelField).append(" >= 0\">\n");
//                            }else{
//                                update.append("            <if test=\"").append(camelField).append(" != 0\">\n");
//                            }
//                        }

                        update.append("                ").append(field).append("=").append("#{").append(camelField).append("},\n");
                        update.append("            </if>\n");
                        insertPart = update;
                    }
                }

                buf.append(")\n        values (").append(insert.toString()).append(")\n");
                buf.append("    </insert>\n\n");

                buf.append("    <sql id=\"baseSet\">\n");
                buf.append("        <set>\n");
                buf.append(update);
                buf.append("        </set>\n");
                buf.append("    </sql>\n\n");

                buf.append("    <update id=\"update\" parameterType=\"").append(beanName).append("\">\n");
                buf.append("        update ").append(dbTable).append(" \n");
                buf.append("         <include refid=\"baseSet\"/>\n");
                buf.append("        where ").append(keyName).append("=#{").append(camelKey).append("}\n");
                buf.append("    </update>\n\n");

                buf.append("    <insert id=\"insertPart\" useGeneratedKeys=\"true\" keyProperty=\"id\" parameterType=\"").append(beanName).append("\">\n");
                buf.append("        insert ").append(dbTable).append(" \n");
                /*buf.append("        <set>\n");
                buf.append(insertPart);
                buf.append("        </set>\n");*/
                buf.append("         <include refid=\"baseSet\"/>\n");
                buf.append("    </insert>\n\n");

                buf.append("    <update id=\"updateStatus\">\n");
                buf.append("        update ").append(dbTable).append(" \n");
                buf.append("        set status = #{status}\n");
                buf.append("        where ").append(keyName).append("= #{").append(camelKey).append("}\n");
                buf.append("    </update>\n\n");


                buf.append("    <update id=\"delete\">\n");
                buf.append("        update ").append(dbTable).append(" \n");
                buf.append("        set deleted = 1\n");
                buf.append("        where ").append(keyName).append("= #{").append(camelKey).append("}\n");
                buf.append("    </update>\n\n");

                buf.append("</mapper>");
                FileUtil.writIn(xml, buf.toString());
                System.out.println(xml + "生成完毕。");
            }
        } catch (Exception var18) {
            var18.printStackTrace();
        }

    }

    /**
     * 数据库中获得comment属性值
     *
     * @param dbName  数据库名
     * @param dbTable 表名
     * @return
     */
    public List<Map<String, Object>> getComment(String dbName, String dbTable) {
        String sqlColComment = "SELECT column_name,column_comment FROM information_schema.columns WHERE table_schema = \"" + dbName + "\" and table_name = \"" + dbTable + "\" order by ORDINAL_POSITION";
        JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
        List<Map<String, Object>> list = jdbc.queryForList(sqlColComment);
        return list;
    }

    /**
     * 两个集合合并
     *
     * @param messList    表信息集合
     * @param commentList 注释集合
     * @return
     */
    public List<Map<String, Object>> mergeList(List<Map<String, Object>> messList, List<Map<String, Object>> commentList) {

        if (messList != null && commentList != null) {
            for (int i = 0; i < commentList.size(); i++) {
                messList.get(i).put("column_comment", commentList.get(i).get("column_comment"));
            }
        }
        return messList;
    }

    private String getType(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        } else {
            str = str.toLowerCase();
            int i = 0;

            for (int n = intType.length; i < n; ++i) {
                if (str.startsWith(intType[i])) {
                    return "Integer";
                }
            }

            return str.startsWith("bigint") ? "Long" : (!str.startsWith("float") && !str.startsWith("decimal") ? (str.startsWith("double") ? "Double" : "String") : "Float");
        }
    }

    private static String setFirstCharUpcase(String s) {
        if (s != null && s.length() >= 1) {
            char[] c = s.toCharArray();
            if (c.length > 0 && c[0] >= 97 && c[0] <= 122) {
                c[0] = (char) ((short) c[0] - 32);
            }

            return String.valueOf(c);
        } else {
            return s;
        }
    }

    private static String setFirstLowerCase(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    private static String setPascalCase(String str) {
        String[] arr = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(setFirstCharUpcase(s));
        }
        return sb.toString();
    }


    private static String setCamelCase(String str) {
        String[] arr = str.split("_");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(setFirstCharUpcase(arr[i]));
        }
        return sb.toString();

    }

    public void createNew(String serviceName, String tableName, String databaseName) {
        String domainDir = "up-ecommerce-" + serviceName + "-service";
        String mapperDir = "resources/mybatis-mapper";
        String dbTable = tableName;
        String dbBean = tableName;
        String packagePrefix = "com.up.e.commerce." + serviceName + ".service";
        String rpcDir = "up-ecommerce-" + serviceName + "-rpc";
        String rpcPrefix = "com.up.e.commerce." + serviceName + ".rpc";
        String dbName = databaseName;
        createNew(domainDir, mapperDir, dbTable, dbBean, packagePrefix, rpcDir, rpcPrefix, null, null, dbName);
    }

    public void createNewControllerDto(String serviceName, String tableName, String databaseName) {
        String domainDir = "up-ecommerce-" + serviceName + "-service";
        String mapperDir = "resources/mybatis-mapper";
        String dbTable = tableName;
        String dbBean = tableName;
        String packagePrefix = "com.up.e.commerce." + serviceName + ".service";
        String rpcDir = "up-ecommerce-" + serviceName + "-rpc";
        String rpcPrefix = "com.up.e.commerce." + serviceName + ".rpc";
        String dbName = databaseName;
        createNewControllerDto(domainDir, mapperDir, dbTable, dbBean, packagePrefix, rpcDir, rpcPrefix, dbName);
    }
}
