package pro.yuchen.demo.java8.chapter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-05-25 11:29
 */
public class Demo_002 {

	public static void main(String[] args) throws Exception {

		String pdfPath = "/Users/Selfimpr/Desktop/letter.pdf";
		String imagePath = "/Users/Selfimpr/Desktop/confirmation.jpg";
		String outPath = "/Users/Selfimpr/Desktop/test.pdf";

		/*
		// 水印是虚的, 而且免费版有版权信息
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(pdfPath);
		PdfPageBase page = pdf.getPages().get(0);
		page.setBackgroundImage(imagePath);
		pdf.saveToFile(outPath);
		pdf.close();
		*/

		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
		document.open();
		




	}

}
