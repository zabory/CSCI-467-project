package packing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Database.Records.PartRecord;

public class PackingListGenerator {
	
	public static ByteArrayInputStream GeneratePackingList (String customer, String address, String city, LinkedList<PartRecord> parts, HashMap<Integer, Integer> partCounts) {
		
		
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			
			/**
			 * Add content here!
			 */
			
			// table stuff
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(60);
			table.setWidths(new float[] { 1, 3.5f, 1 });
			
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Quantity", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			for (PartRecord part : parts) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(part.getNumber() + ""));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(part.getDescription()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(partCounts.get(part.getNumber()) + ""));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}
			// end table stuff
			
			// customer stuff
			PdfPTable tableC = new PdfPTable(2);
			tableC.setWidthPercentage(80);
			tableC.setWidths(new int[] {3, 3});
			
			hcell = new PdfPCell(new Phrase("Customer name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableC.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer address", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableC.addCell(hcell);
			
			PdfPCell cell;
			
			cell = new PdfPCell(new Phrase(customer));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableC.addCell(cell);
			
			cell = new PdfPCell(new Phrase(address + " " + city));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableC.addCell(cell);
			// end customer stuff
			
			// border stuff
			Rectangle r = new Rectangle(34, 34, document.right(), document.top());
			r.setBorder(Rectangle.BOX);
			r.setBorderWidth(2);
			// end border stuff
			
			// image stuff
			try {
				Image image = Image.getInstance(PackingListGenerator.class.getResource("/car.png"));
				image.scalePercent(35);
				image.setAbsolutePosition(34, document.top() - 125);
				document.add(image);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			// end image stuff
			
			document.add(r);
			
			document.add(new Paragraph("\n "));
			Paragraph p = new Paragraph("Bad Car Parts R' Us", new Font(FontFamily.TIMES_ROMAN, 45.0f, Font.UNDERLINE, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.add(new Paragraph("\n "));
			
			// add space between title and table
			document.add(new Paragraph("\n "));
			document.add(tableC);
			
			// add space between customer and table
			document.add(new Paragraph(" "));
			document.add(table);
			
			
			document.add(r);
			
			
			document.close();

		} catch (DocumentException ex) {

		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
