package com.estudo.api.util;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.estudo.api.commons.Translator;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.apache.commons.lang3.SystemUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PdfUtils {

    public Pdf createPdfFile(){
        try{
            String file = "";
            if (SystemUtils.IS_OS_WINDOWS) {
                file = getClass().getResource("/wkhtmltox/wkhtmltopdf.exe").toString();
                file = file.replace("file:/", "");
            } else if (SystemUtils.IS_OS_LINUX) {
                file = getClass().getResource("/wkhtmltox/wkhtmltopdf.sh").toString();
                file = file.replace("file:/", "");
            } else {
                String[] array = {System.getProperty("os.name")};
                throw new ObjectNotFoundException("{oai.modeloImpressao.exception.osNaomapeado}", array);
            }

            Pdf pdf = new Pdf(new WrapperConfig(file));
            return pdf;

        } catch (Exception e){
            e.printStackTrace();
            throw new ObjectNotFoundException(Translator.toLocale("{oai.pdf.exception.ErrorCreatePdf}"));
        }
    }

    public void addNewPagePdf(Pdf pdf, String dataPage){
        try{
            pdf.addPageFromString(dataPage);
        } catch (Exception e){
            e.printStackTrace();
            throw new ObjectNotFoundException(Translator.toLocale("oai.pdf.exception.ErrorAddPagePdf"));
        }
    }

    public String getBase64FromPdf(Pdf pdf){
        try {
            return Base64.getEncoder().encodeToString(pdf.getPDF());
        } catch (InterruptedException e) {
            throw new ObjectNotFoundException(Translator.toLocale("{oai.pdf.exception.erroInterrupted}"));
        } catch (IOException e) {
            throw new ObjectNotFoundException(Translator.toLocale("{oai.pdf.exception.erroIOException}"));
        }
    }

    public byte[] getPdf(Pdf pdf){
        try {
            return pdf.getPDF();
        } catch (InterruptedException e) {
            throw new ObjectNotFoundException(Translator.toLocale("{oai.pdf.exception.erroInterrupted}"));
        } catch (IOException e) {
            throw new ObjectNotFoundException(Translator.toLocale("{oai.pdf.exception.erroIOException}"));
        }
    }

    public void addNewPagePdfFromBase64(Pdf pdf, String base64File){
        try{
            byte[] decode = Base64.getDecoder().decode(base64File);
            String htmlToPdf = new String(decode, StandardCharsets.UTF_8);
            addNewPagePdf(pdf, htmlToPdf);
        }catch (Exception e){
            e.printStackTrace();
            throw new ObjectNotFoundException(Translator.toLocale("oai.pdf.exception.erroAddPageB64Pdf"));
        }
    }
}
