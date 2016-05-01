
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author khyati
 */
public class enjutoberkley_stringrep {

    public static void main(String[] args) {
        // TODO code application logic here

        BufferedReader br = null;
        BufferedWriter bw = null;
        StringBuffer berkley_out = new StringBuffer();
        String enju_output = "", cons_cat = "",xcons_cat="", tok_cat = "", token = "";
        int str_index_cons = 0,strt_xindex_cons=0;
        int end_index_cons = 0,end_xindex_cons=0;
        int str_index_tok = 0;
        int end_index_tok = 0;
        int str_index_tokword = 0, toplevel_index = 0;
        int end_index_tokword = 0;
        String cons_pos = "";
        try {
            split_enju_sentence();
            //   System.out.println(enju_output);
            
            br = new BufferedReader(new FileReader("D:\\NetBeansProjects\\khyati\\src\\khyati\\new_frmt_enju.en"));
            bw = new BufferedWriter(new FileWriter(new File("D:\\NetBeansProjects\\khyati\\src\\khyati\\berkfrmt_wid_conspos.en")));

            while ((enju_output = br.readLine()) != null) {

                if (enju_output.contains("<sentence")) {
                    berkley_out.append("<tree label=\"TOP\"> ").toString();
                }
                if (enju_output.contains("<cons")) {
                    str_index_cons = enju_output.indexOf("cat=\"") + 5;
                    end_index_cons = enju_output.indexOf("\"", str_index_cons);
                    cons_cat = enju_output.substring(str_index_cons, end_index_cons);
                    strt_xindex_cons = enju_output.indexOf("xcat=\"") + 6;
                    end_xindex_cons = enju_output.indexOf("\"", strt_xindex_cons);
                    xcons_cat = enju_output.substring(strt_xindex_cons, end_xindex_cons);
                    //System.out.println("cons cat="+cons_cat);
                   cons_pos=find_penncat_of_cons(cons_cat,xcons_cat);
                   berkley_out.append("<tree label=\"").append(cons_pos).append("\"> ").toString();
                   // berkley_out.append("<cons cat=\"").append(cons_cat).append("\" xcat=\"").append(xcons_cat).append("\" penn cat=\"").append(cons_pos).append("\"");
                }

                if (enju_output.contains("<tok")) {
                    str_index_tok = enju_output.indexOf("pos=\"") + 5;
                    end_index_tok = enju_output.indexOf("\"", str_index_tok);
                    str_index_tokword = enju_output.indexOf(">") + 1;
                    end_index_tokword = enju_output.indexOf("</");
                    tok_cat = enju_output.substring(str_index_tok, end_index_tok);
                    token = enju_output.substring(str_index_tokword, end_index_tokword);
                   berkley_out.append("<tree label=\"").append(tok_cat).append("\"> ").append(token).append(" ").toString();
                    
                }
                if (enju_output.contains("</tok")) {
                    berkley_out.append("</tree> ").toString();
                }
                if (enju_output.contains("</cons")) {
                   berkley_out.append("</tree> ").toString();
                }
                if (enju_output.contains("</sentence>")) {
                   berkley_out.append("</tree> ").toString();
                }
                //System.out.println("\n");
                // berkley_out.setCharAt(toplevel_index, '\n');
            }

            String result = convert_to_berkley_format(berkley_out.toString());
            bw.write(result);
            bw.close();
            br.close();

            //System.out.println(result); 
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void split_enju_sentence() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        String enju_frmt = "";
        String enju_modified = "", enju_final = "";
        FileReader fr=null;
        FileWriter fw=null;
        try {
            fr=new FileReader("D:\\NetBeansProjects\\khyati\\src\\khyati\\enju_output.en");
            br = new BufferedReader(fr);
            fw=new FileWriter("D:\\NetBeansProjects\\khyati\\src\\khyati\\new_frmt_enju.en");
            bw = new BufferedWriter(fw);
            while ((enju_frmt = br.readLine()) != null) {
                enju_modified += enju_frmt.replaceAll("> <", "><").replaceAll("><", ">\n<");
            }
            enju_final += enju_modified.replaceAll("><", ">\n<");
            bw.write(enju_final);
            //zipFile();
            bw.close();
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static String convert_to_berkley_format(String berkley_output) {
        String in = berkley_output;
        String output = in.replaceAll("<tree label=\"TOP\">", "\n<tree label=\"TOP\">");
        return output;
    }



    private static String find_penncat_of_cons(String cons_cat,String xcons_cat) {
       String penn_cat="",enju_cat=cons_cat,enju_xcons_cat=xcons_cat;
       if(enju_cat.equalsIgnoreCase("ADJP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="ADJP"; 
       }
       
       else if(enju_cat.equalsIgnoreCase("ADJP") && enju_xcons_cat.equalsIgnoreCase("REL")){
          penn_cat="WHADJP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADJP") && enju_xcons_cat.equalsIgnoreCase("FREL")){
          penn_cat="WHADJP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADJP") && enju_xcons_cat.equalsIgnoreCase("WH")){
          penn_cat="WHADJP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADVP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="ADVP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADVP") && enju_xcons_cat.equalsIgnoreCase("REL")){
          penn_cat="WHADVP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADVP") && enju_xcons_cat.equalsIgnoreCase("FREL")){
          penn_cat="WHADVP"; 
       }
       else if(enju_cat.equalsIgnoreCase("ADVP") && enju_xcons_cat.equalsIgnoreCase("WH")){
          penn_cat="WHADVP"; 
       }
       else if(enju_cat.equalsIgnoreCase("CONJP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="CONJP"; 
       }
       else if(enju_cat.equalsIgnoreCase("CP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="SBAR"; 
       }
       else if(enju_cat.equalsIgnoreCase("DP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="NP"; 
       }
       else if(enju_cat.equalsIgnoreCase("NP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="NP"; 
       }
       else if(enju_cat.equalsIgnoreCase("NX") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="NX"; 
       }
       else if(enju_cat.equalsIgnoreCase("NP") && enju_xcons_cat.equalsIgnoreCase("REL")){
          penn_cat="WHNP"; 
       }
       else if(enju_cat.equalsIgnoreCase("NP") && enju_xcons_cat.equalsIgnoreCase("FREL")){
          penn_cat="WHNP "; 
       }
       else if(enju_cat.equalsIgnoreCase("NP") && enju_xcons_cat.equalsIgnoreCase("WH")){
          penn_cat="WHNP "; 
       }
       else if(enju_cat.equalsIgnoreCase("PP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="PP"; 
       }
       else if(enju_cat.equalsIgnoreCase("PP") && enju_xcons_cat.equalsIgnoreCase("REL")){
          penn_cat="WHPP"; 
       }
       else if(enju_cat.equalsIgnoreCase("PP") && enju_xcons_cat.equalsIgnoreCase("WH")){
          penn_cat="WHPP"; 
       }
       else if(enju_cat.equalsIgnoreCase("PRT") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="PRT"; 
       }
       else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="S"; 
       }
       else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("INV")){
          penn_cat="SINV"; 
       }
        else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("Q")){
          penn_cat="SQ"; 
       }
         else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("REL")){
          penn_cat="SBAR"; 
       }
          else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("FREL")){
          penn_cat="SBAR"; 
       }
           else if(enju_cat.equalsIgnoreCase("S") && enju_xcons_cat.equalsIgnoreCase("WH")){
          penn_cat="SBARQ"; 
       }
            else if(enju_cat.equalsIgnoreCase("SCP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="SBAR"; 
       }
             else if(enju_cat.equalsIgnoreCase("VP") && enju_xcons_cat.equalsIgnoreCase("")){
          penn_cat="VP"; 
       }
       else{
           penn_cat=enju_cat;
       }
       return penn_cat;
    }

}
