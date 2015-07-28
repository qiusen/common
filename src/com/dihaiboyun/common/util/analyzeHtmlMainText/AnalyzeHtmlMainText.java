/**
 * 使用HtmlParser获取网页正文件
 * 需 htmlparser.jar
 */
package com.dihaiboyun.common.util.analyzeHtmlMainText;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;


/**
 * @author qiusen
 *
 */
public class AnalyzeHtmlMainText {

	protected static final String lineSign = System.getProperty("line.separator");
    protected static final int lineSign_size = lineSign.length();
	/**
	 * 
	 */
	public AnalyzeHtmlMainText() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取网页源码
	 * @param sourceUrl
	 * @param encoding
	 * @return
	 */
	public static String getHtmlText(String sourceUrl,String encoding) throws Exception {
		System.out.println("sourceUrl: "+sourceUrl);
		System.out.println("encoding: "+encoding);
		
		String content = null;
		HttpURLConnection conn = null;
		try{
			URL newsurl=new URL(sourceUrl);
			conn = (HttpURLConnection)newsurl.openConnection();
			
			HttpURLConnection.setFollowRedirects(true);		//所有的http连接是否自动处理重定向
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			
			conn.setRequestProperty("User-agent","Mozilla/4.0");

			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			
			Parser parser = new Parser(conn);
			if(encoding!=null && encoding.trim().length()>0){
				parser.setEncoding(encoding);
			}
			
			for (NodeIterator e = parser.elements(); e.hasMoreNodes();) {
				Node node = (Node) e.nextNode();
				
                if (node instanceof Html) {
//	                	System.out.println("++++++++++++++++++");
	                	StringBuffer strbuf = new StringBuffer();
	                	filterScript(node.toHtml(), strbuf);
	                	filterStyle(strbuf.toString(), strbuf);
	                	content = strbuf.toString();
//	                	System.out.println(content);
	                	//过滤script, style
//	                	System.out.println("++++++++++++++++++");
                }
			}
			
			
		}catch(Exception e){
//			System.out.println(sourceUrl+" "+Thread.currentThread().getName()+" 爬取出错...");
//			e.printStackTrace();
			throw e;
		}
		
		return content;
	}
	
	/**
	 * 过滤Javascript
	 * @param content
	 * @param strbuf
	 */
	public static void filterScript(String content, StringBuffer strbuf){
//		System.out.println(content);
		int begin = content.toLowerCase().indexOf("<script");
		int end = content.toLowerCase().indexOf("</script>");
//		System.out.println("begin: " + begin);
//		System.out.println("end: " + end);
		if(begin>=0 && end >begin){	//正常顺序
			strbuf.append(content.substring(0,begin));
			filterScript(content.substring(end+9, content.length()), strbuf);
		}else if(begin>=0 && end <begin){	//先有</script>，后面有<script>
			filterScript(content.substring(end+9, content.length()), strbuf);
		}else if(begin<0 && end <begin){		//只有</script>了
			filterScript(content.substring(end+9, content.length()), strbuf);
		}else{
			strbuf.append(content);
		}
		
	}
	
	/**
	 * 过滤Style
	 * @param content
	 * @param strbuf
	 */
	public static void filterStyle(String content, StringBuffer strbuf){
//		System.out.println(content);
		int begin = content.toLowerCase().indexOf("<style");
		int end = content.toLowerCase().indexOf("</style>");
//		System.out.println("begin: " + begin);
//		System.out.println("end: " + end);
		if(begin>=0 && end >begin){	//正常顺序
			strbuf.append(content.substring(0,begin));
			filterStyle(content.substring(end+8, content.length()), strbuf);
		}else if(begin>=0 && end <begin){	//先有</script>，后面有<script>
			filterStyle(content.substring(end+8, content.length()), strbuf);
		}else if(begin<0 && end <begin){		//只有</script>了
			filterStyle(content.substring(end+8, content.length()), strbuf);
		}else{
			strbuf.append(content);
		}
		
	}
	
	
	
	public static String getHtmlMainText(String content,String sourceUrl,String encoding,String startP,String endP) throws Exception{
		
		try{
			Parser parser = new Parser(content);
			for (NodeIterator e = parser.elements(); e.hasMoreNodes();) {
				Node node = (Node) e.nextNode();
				
                if (node instanceof Html) {
	                	PageContext context = new PageContext();
                    context.setNumber(0);
                    context.setTextBuffer(new StringBuffer());
                    //抓取出内容
                    parser.elements();
//                    List list = 
                    		extractHtml(node,context,sourceUrl,startP,endP);
//                    System.out.println("list.size " + list.size());
                    content = context.getTextBuffer().toString();
//                    System.out.println("content " + content);
                }
			}
			
			
		}catch(Exception e){
//			System.out.println(sourceUrl+" "+Thread.currentThread().getName()+" 爬取出错...");
//			e.printStackTrace();
			throw e;
		}
		
		return content;
	}
	
	
	public static List extractHtml(Node nodeP, PageContext context, String sourceUrl,String startP,String endP){
//		System.out.println(nodeP.getText() + "-----nodeP.getText():" + nodeP.toHtml()); 
		NodeList nodeList = nodeP.getChildren();
        boolean bl = false;

        
        
        
        if ((nodeList == null) || (nodeList.size() == 0)) {	//无子节点
            if (nodeP instanceof ParagraphTag) {				//空<p></p>
                ArrayList<StringBuffer> tableList = new ArrayList<StringBuffer>();
                StringBuffer temp = new StringBuffer();
                temp.append(startP);
                tableList.add(temp);
                temp = new StringBuffer();
                temp.append(endP);
                tableList.add(temp);

                return tableList;
            }

            return null;
        }
        


		//清理css,javascript begin
		
		if(nodeP instanceof ScriptTag){
			System.out.println("---------------" + nodeP.getText());
			return null;
		}
		
		//清理css,javascript end
		
        
        

        if ((nodeP instanceof TableTag) || (nodeP instanceof Div)) {
            bl = true;
        }

        if (nodeP instanceof ParagraphTag) {
            ArrayList<StringBuffer> tableList = new ArrayList<StringBuffer>();
            StringBuffer temp = new StringBuffer();
            temp.append(startP);
            tableList.add(temp);
            extractParagraph(nodeP, sourceUrl, tableList,startP,endP);

            temp = new StringBuffer();
            temp.append(endP);

            tableList.add(temp);

            return tableList;
        }

        ArrayList tableList = new ArrayList();

        try {
            for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                Node node = (Node) e.nextNode();

                if (node instanceof LinkTag) {
                    tableList.add(node);
                    setLinkImg(node, sourceUrl);
                } else if (node instanceof ImageTag) {
                    ImageTag img = (ImageTag) node;

                    if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
                        img.setImageURL(sourceUrl + img.getImageURL());
                    } else {
                        img.setImageURL(img.getImageURL());
                    }

                    tableList.add(node);
                } else if (node instanceof ScriptTag ||
                        node instanceof StyleTag || node instanceof SelectTag) {
                		
//                		System.out.println("-----nodeP.getText():" + node.getText() +"-----" + node.toHtml()); 
                	
                	
                } else if (node instanceof TextNode) {
                    if (node.getText().length() > 0) {
                        StringBuffer temp = new StringBuffer();
                        String text = collapse(node.getText()
                                                   .replaceAll("&nbsp;", "")
                                                   .replaceAll("　", ""));

                        temp.append(text.trim());

                        tableList.add(temp);
                    }
                } else {
                    if (node instanceof TableTag || node instanceof Div) {
                        TableValid tableValid = new TableValid();
                        isValidTable(node, tableValid);

                        if (tableValid.getTrnum() > 2) {
                            tableList.add(node);

                            continue;
                        }
                    }

                    List tempList = extractHtml(node, context, sourceUrl, startP, endP);

                    if ((tempList != null) && (tempList.size() > 0)) {
                        Iterator ti = tempList.iterator();

                        while (ti.hasNext()) {
                            tableList.add(ti.next());
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        if ((tableList != null) && (tableList.size() > 0)) {
            if (bl) {
                StringBuffer temp = new StringBuffer();
                Iterator ti = tableList.iterator();
                int wordSize = 0;
                StringBuffer node;
                int status = 0;
                StringBuffer lineStart = new StringBuffer(startP);
                StringBuffer lineEnd = new StringBuffer(endP);

                while (ti.hasNext()) {
                    Object k = ti.next();

                    if (k instanceof LinkTag) {
//                        if (status == 0) {
//                            temp.append(lineStart);
//                            status = 1;
//                        }
//
//                        node = new StringBuffer(((LinkTag) k).toHtml());
//                        temp.append(node);
                    } else if (k instanceof ImageTag) {
                        if (status == 0) {
                            temp.append(lineStart);
                            status = 1;
                        }
                        ImageTag it = (ImageTag) k;
//                        System.out.println(it.getAttribute("src")+" ...");
                        node = new StringBuffer("<img src="+it.getAttribute("src")+" >");//标准化图片格式

//                        node = new StringBuffer(((ImageTag) k).toHtml());
                        temp.append(node);
                    } else if (k instanceof TableTag) {
                        if (status == 0) {
                            temp.append(lineStart);
                            status = 1;
                        }

                        node = new StringBuffer(((TableTag) k).toHtml());
                        temp.append(node);
                    } else if (k instanceof Div) {
                        if (status == 0) {
                            temp.append(lineStart);
                            status = 1;
                        }

                        node = new StringBuffer(((Div) k).toHtml());
                        temp.append(node);
                    } else {
                        node = (StringBuffer) k;

                        if (status == 0) {
                            if (node.indexOf("<p") < 0) {
                                temp.append(lineStart);
                                temp.append(node);
                                wordSize = wordSize + node.length();
                                status = 1;
                            } else {
                                temp.append(node);
                                status = 1;
                            }
                        } else if (status == 1) {
                            if (node.indexOf("</p") < 0) {
                                if (node.indexOf("<p") < 0) {
                                    temp.append(node);
                                    wordSize = wordSize + node.length();
                                } else {
                                    temp.append(lineEnd);
                                    temp.append(node);
                                    status = 1;
                                }
                            } else {
                                temp.append(node);
                                status = 0;
                            }
                        }
                    }
                }

                if (status == 1) {
                    temp.append(lineEnd);
                }

                if (wordSize > context.getNumber()) {
                    context.setNumber(wordSize);
                    context.setTextBuffer(temp);
                }

                return null;
            } else {
                return tableList;
            }
        }

        return null;
	}
	
	
	/**
    * 设置图象连接
    * @param nodeP
    * @param siteUrl
    */
    private static void setLinkImg(Node nodeP, String siteUrl) {
        NodeList nodeList = nodeP.getChildren();

        try {
            for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                Node node = (Node) e.nextNode();

                if (node instanceof ImageTag) {
                    ImageTag img = (ImageTag) node;

                    if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
                        img.setImageURL(siteUrl + img.getImageURL());
                    } else {
                        img.setImageURL(img.getImageURL());
                    }
                }
            }
        } catch (Exception e) {
            return;
        }

        return;
    }
    
    
    /**
     * 钻取段落中的内容
     * @param nodeP
     * @param sourceUrl
     * @param tableList
     * @return
     */
     private static List extractParagraph(Node nodeP, String sourceUrl, List tableList,String startP,String endP) {
    	 	NodeList nodeList = nodeP.getChildren();

         if ((nodeList == null) || (nodeList.size() == 0)) {
             if (nodeP instanceof ParagraphTag) {
                 StringBuffer temp = new StringBuffer();
                 temp.append(startP);
                 tableList.add(temp);
                 temp = new StringBuffer();
                 temp.append(endP);
                 tableList.add(temp);

                 return tableList;
             }

             return null;
         }
//         System.out.println(siteUrl);
         try {
             for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                 Node node = (Node) e.nextNode();

                 if (node instanceof ScriptTag || node instanceof StyleTag ||
                         node instanceof SelectTag) {
//                 } else if (node instanceof LinkTag) {	//过滤超链接
//                     tableList.add(node);
//                     setLinkImg(node, siteUrl);
                 } else if (node instanceof ImageTag) {
                     ImageTag img = (ImageTag) node;
                     
                     if (img.getImageURL().toLowerCase().indexOf("http://") < 0) {
                         URI base =  new URI(sourceUrl);                         
                         img.setImageURL(base.resolve(img.getImageURL()).toString());
                     } else {
                         img.setImageURL(img.getImageURL());
                     }

                     tableList.add(node);
                 } else if (node instanceof TextNode) {
                     if (node.getText().trim().length() > 0) {
                         String text = collapse(node.getText()
                                                    .replaceAll("&nbsp;", "")
                                                    .replaceAll("　", ""));
                         StringBuffer temp = new StringBuffer();
                         temp.append(text);
                         tableList.add(temp);
                     }
                 } else if (node instanceof Span) {
                     StringBuffer spanWord = new StringBuffer();
                     getSpanWord(node, spanWord);

                     if ((spanWord != null) && (spanWord.length() > 0)) {
                         String text = collapse(spanWord.toString()
                                                        .replaceAll("&nbsp;", "")
                                                        .replaceAll("　", ""));

                         StringBuffer temp = new StringBuffer();
                         temp.append(text);
                         tableList.add(temp);
                     }
                 } else if (node instanceof TagNode) {
                     String tag = node.toHtml();

                     if (tag.length() <= 10) {
                         tag = tag.toLowerCase();

	                         if ((tag.indexOf("strong") >= 0) ||
	                                 (tag.indexOf("b") >= 0)) {
	                             StringBuffer temp = new StringBuffer();
	                             temp.append(tag);
	                             tableList.add(temp);
	                         }
		                 } else if (node instanceof ScriptTag ||
		                             node instanceof StyleTag || node instanceof SelectTag) {
                 	
	                 	}  else {
	                	 
	                         if (node instanceof TableTag || node instanceof Div) {
	                             TableValid tableValid = new TableValid();
	                             isValidTable(node, tableValid);
	
	                             if (tableValid.getTrnum() > 2) {
	                                 tableList.add(node);
	
	                                 continue;
	                             }
	                         }

                         extractParagraph(node, sourceUrl, tableList,startP,endP);
                     }
                 }
             }
         } catch (Exception e) {
             return null;
         }

         return tableList;
     }
     
     
     protected static void getSpanWord(Node nodeP, StringBuffer spanWord) {
         NodeList nodeList = nodeP.getChildren();

         try {
             for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                 Node node = (Node) e.nextNode();

                 if (node instanceof ScriptTag || node instanceof StyleTag ||
                         node instanceof SelectTag) {
                 } else if (node instanceof TextNode) {
                     spanWord.append(node.getText());
                 } else if (node instanceof Span) {
                     getSpanWord(node, spanWord);
                 } else if (node instanceof ParagraphTag) {
                     getSpanWord(node, spanWord);
                 } else if (node instanceof TagNode) {
                     String tag = node.toHtml().toLowerCase();

                     if (tag.length() <= 10) {
                         if ((tag.indexOf("strong") >= 0) ||
                                 (tag.indexOf("b") >= 0)) {
                             spanWord.append(tag);
                         }
                     }
                 }
             }
         } catch (Exception e) {
         }

         return;
     }
     /**
      * 判断TABLE是否是表单
      * @param nodeP
      * @return
      */
      private static void isValidTable(Node nodeP, TableValid tableValid) {
          NodeList nodeList = nodeP.getChildren();

          /**如果该表单没有子节点则返回**/
          if ((nodeList == null) || (nodeList.size() == 0)) {
              return;
          }

          try {
              for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                  Node node = (Node) e.nextNode();

                  /**如果子节点本身也是表单则返回**/
                  if (node instanceof TableTag || node instanceof Div) {
                      return;
                  } else if (node instanceof ScriptTag ||
                          node instanceof StyleTag || node instanceof SelectTag) {
                      return;
                  } else if (node instanceof TableColumn) {
                      return;
                  } else if (node instanceof TableRow) {
                      TableColumnValid tcValid = new TableColumnValid();
                      tcValid.setValid(true);
                      findTD(node, tcValid);

                      if (tcValid.isValid()) {
                          if (tcValid.getTdNum() < 2) {
                              if (tableValid.getTdnum() > 0) {
                                  return;
                              } else {
                                  continue;
                              }
                          } else {
                              if (tableValid.getTdnum() == 0) {
                                  tableValid.setTdnum(tcValid.getTdNum());
                                  tableValid.setTrnum(tableValid.getTrnum() + 1);
                              } else {
                                  if (tableValid.getTdnum() == tcValid.getTdNum()) {
                                      tableValid.setTrnum(tableValid.getTrnum() +
                                          1);
                                  } else {
                                      return;
                                  }
                              }
                          }
                      }
                  } else {
                      isValidTable(node, tableValid);
                  }
              }
          } catch (Exception e) {
              return;
          }

          return;
      }
      /**
       * 判断是否有效TR
       * @param nodeP
       * @param TcValid
       * @return
       */
       private static void findTD(Node nodeP, TableColumnValid tcValid) {
           NodeList nodeList = nodeP.getChildren();

           /**如果该表单没有子节点则返回**/
           if ((nodeList == null) || (nodeList.size() == 0)) {
               return;
           }

           try {
               for (NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                   Node node = (Node) e.nextNode();

                   /**如果有嵌套表单**/
                   if (node instanceof TableTag || node instanceof Div ||
                           node instanceof TableRow ||
                           node instanceof TableHeader) {
                       tcValid.setValid(false);

                       return;
                   } else if (node instanceof ScriptTag ||
                           node instanceof StyleTag || node instanceof SelectTag) {
                       tcValid.setValid(false);

                       return;
                   } else if (node instanceof TableColumn) {
                       tcValid.setTdNum(tcValid.getTdNum() + 1);
                   } else {
                       findTD(node, tcValid);
                   }
               }
           } catch (Exception e) {
               tcValid.setValid(false);

               return;
           }

           return;
       }
       
       protected static String collapse(String string) {
           int chars;
           int length;
           int state;
           char character;
           StringBuffer buffer = new StringBuffer();
           chars = string.length();

           if (0 != chars) {
               length = buffer.length();
               state = ((0 == length) || (buffer.charAt(length - 1) == ' ') ||
                   ((lineSign_size <= length) &&
                   buffer.substring(length - lineSign_size, length).equals(lineSign)))
                   ? 0 : 1;

               for (int i = 0; i < chars; i++) {
                   character = string.charAt(i);

                   switch (character) {
                   case '\u0020':
                   case '\u0009':
                   case '\u000C':
                   case '\u200B':
                   case '\u00a0':
                   case '\r':
                   case '\n':

                       if (0 != state) {
                           state = 1;
                       }

                       break;

                   default:

                       if (1 == state) {
                           buffer.append(' ');
                       }

                       state = 2;
                       buffer.append(character);
                   }
               }
           }

           return buffer.toString();
       }

       
       
       
       
       public String resetCharset(Parser parser) { 
    	   String c = null;
    	   String charset = "gb2312"; 
           String CHARSET_STRING = "charset"; 
           try { 
               parser.setEncoding(charset); 
               NodeFilter metaFilter = new NodeClassFilter(MetaTag.class); 
               NodeList meta = parser.extractAllNodesThatMatch(metaFilter); 
               for (int i = 0; i  < meta.size(); i++) { 
                   MetaTag metaTag = (MetaTag) meta.elementAt(i); 
                   if ((metaTag.getAttribute("http-equiv") != null) && (metaTag.getAttribute("http-equiv").equalsIgnoreCase("content-type"))) { 
                       String content = metaTag.getAttribute("content"); 
                       if (null != content) { 
                           int index = content.toLowerCase().indexOf(CHARSET_STRING); 

                           if (index != -1){ 
                               //下面这一段 org.htmlparser.lexer.page.getCharset() 
                               content = content.substring(index + CHARSET_STRING.length()).trim(); 
                               if (content.startsWith("=")) { 
                                   content = content.substring(1).trim(); 
                                   index = content.indexOf(";"); 
                                   if (index != -1) { 
                                       content = content.substring(0, index); 
                                   } 
                                   if (content.startsWith("\"") && content.endsWith("\"") && (1  < content.length())) { 
                                       content = content.substring(1, content.length() - 1); 
                                   } 

                                   if (content.startsWith("'") && content.endsWith("'") && (1  < content.length())) { 
                                       content = content.substring(1, content.length() - 1); 
                                   } 
                               } 
                               c = content; 
                           } 
                       } 
                   } 
               } 
           } catch (final Exception e) { 
               e.printStackTrace(); 
           } 
           /*
            * final Parser parser = new Parser("http://forum.csdn.net/"); 
            * this.resetCharset(parser); 
            * parser.setEncoding(this.charset); //TestPage.java:Line 102 
            */
           return c;
       } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			//
			String souceUrl = "http://news.qq.com/a/20080519/000806.htm";
			String content = getHtmlText(souceUrl,"gb2312");
			content = getHtmlMainText(content, souceUrl,"gb2312","<p style=\"TEXT-INDENT: 2em\">","</p>");
			
//		String content = getHtmlMainText("http://www.12377.cn/txt/2015-01/20/content_7622927.htm","UTF-8","<p style=\"TEXT-INDENT: 2em\">","</p>");
		
//			String content = getHtmlMainText("http://gb.cri.cn/15524/2007/03/21/2385@1508174.htm","gb2312","<p style=\"TEXT-INDENT: 2em\">","</p>");
			System.out.println(content);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
