package pro.yuchen.demo.java8.chapter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-05-25 11:29
 */
public class Demo_002 {

	public static void main(String[] args) throws Exception {

		String pdfPath = "/Users/Selfimpr/Desktop/letter.pdf";
		String imagePath = "/Users/Selfimpr/Desktop/zzz.png";
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

		PdfReader reader = new PdfReader(pdfPath);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outPath));
		PdfContentByte content = stamper.getOverContent(1);
		Image image = Image.getInstance(imagePath);
		image.setAbsolutePosition(0f, 0f);
		image.scaleAbsolute(reader.getCropBox(1).getWidth(), reader.getCropBox(1).getHeight());
		image.setAlignment(Image.ALIGN_CENTER);
//		layer
//		image.setLayer();

		content.addImage(image);
		stamper.close();
		reader.close();

	}


}


class BGPic implements PdfPCellEvent {
	public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
		PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
		Image img = null;
		try {
			img = Image.getInstance("/Users/Selfimpr/Desktop/confirmation.jpg");
			img.scaleAbsolute(80, 100);  // 设置背景图片的大小
			img.setAbsolutePosition(298, 606);  // 设置第一个背景图片的绝对位置
			cb.addImage(img);
			img.setAbsolutePosition(217, 706);  // 设置第二个背景图片的绝对位置
			cb.addImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}