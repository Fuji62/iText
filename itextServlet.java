package to.msn.wings.chap9; /*itextServlet.java*/
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class itextServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		//入力画面から送信されてきた名称を表示
		request.setCharacterEncoding("utf-8");
		String strName = request.getParameter("input_name");

		//出力用のStreameをインスタンス化
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();

		//文書オブジェクトを作成
		//ページサイズ
		Document doc = new Document(PageSize.A4, 50, 50, 50, 50);

		try {
			//アウトプットストリームをPDFWriterに設定
			System.out.print(byteout);
			PdfWriter pdfwriter = PdfWriter.getInstance(doc, byteout);
			System.out.println("●"+pdfwriter);


			//フォント設定
			/* フォント設定部 */
			//(ゴシック15pt(太字)
			Font font_header =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),15,Font.BOLD);
			//ゴシック11pt
			Font font_g11 =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),11);
			//ゴシック10pt
			Font font_g10 =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),10);
			//明朝10pt
			Font font_m10 =
			  new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),10);
			//ゴシック11pt(下線あり)
			Font font_underline_11 =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),11,Font.UNDERLINE);
			//ゴシック11pt(赤)
			Font font_red_11 =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),11);
			font_red_11.setColor(new Color(255,0,0));
			//空白セル用フォント(非表示)
			Font font_empty =
			  new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),9);
			font_empty.setColor(new Color(255,255,255));

			//出力するPDFに説明を付与
			doc.addAuthor("岡 雅久");
			doc.addSubject("iTextサンプル");

			//ヘッダーの設定
			HeaderFooter header = new HeaderFooter(new Phrase("福岡物産商事 月間売上実績", font_header),false);
			header.setAlignment(Element.ALIGN_CENTER);
			doc.setHeader(header);

			//フッターの設定
			HeaderFooter footer = new HeaderFooter(new Phrase("--"), new Phrase("--"));
			footer.setAlignment(Element.ALIGN_CENTER);
			footer.setBorder(Rectangle.NO_BORDER);
			doc.setFooter(footer);

			//文章の出力を開始
			doc.open();

			/*売上実績表のテーブル作成*/
			//帳票明細の条件部分を設定
			doc.add(new Paragraph("2021年 ９月実績", font_red_11));
			Paragraph para_1 = new Paragraph("担当者：" + strName, font_g11);
			para_1.setAlignment(Element.ALIGN_RIGHT);
			doc.add(para_1);
			doc.add(new Paragraph(""));
			Paragraph para_2 = new Paragraph("商品名：歯磨きセット", font_underline_11);
			para_2.setAlignment(Element.ALIGN_LEFT);
			doc.add(para_2);

			//帳票明細の明細行設定
			Table uriage_table = new Table(4);
			uriage_table.setWidth(100); //売上テーブル全体の幅
			int uriage_table_width[] = {11,19,40,30}; //各列の幅
			uriage_table.setWidths(uriage_table_width);

			//テーブルのデフォルト表示位置(横)
			//uriage_table.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
			//テーブルのデフォルト表示位置(縦)
			//uriage_table.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);

			uriage_table.setPadding(3);
			uriage_table.setSpacing(0);
			uriage_table.setBorderColor(new Color(0,0,0));

			//明細表の項目各部分のセルの設定
			Cell cell_11 = new Cell(new Phrase("順位", font_g10));
			cell_11.setGrayFill(0.8f);
			Cell cell_21 = new Cell(new Phrase("顧客コード", font_g10));
			cell_21.setGrayFill(0.8f);
			Cell cell_31 = new Cell(new Phrase("顧客名称", font_g10));
			cell_31.setGrayFill(0.8f);
			Cell cell_41 = new Cell(new Phrase("金額", font_g10));
			cell_41.setGrayFill(0.8f);

			uriage_table.addCell(cell_11); //テーブルにセルを設定
			uriage_table.addCell(cell_21);
			uriage_table.addCell(cell_31);
			uriage_table.addCell(cell_41);

			String[][] uriage_data = { //明細データを指定
					{"1", "AA-001", "〇〇物産", "100,000"},
					{"2", "BB-001", "△△商事", "20,000"},
					{"3", "CC-001", "□□商店", "10,000"},
					{"4", "DD-001", "××実業", "5,000"},
					{"5", "EE-001", "〇〇運送", "1,000"},
					{"6", "FF-001", "▲▲サービス", "500"},
					{"7", "GG-001", "●●システム", "300"},
					{"8", "HH-001", "〇〇産業", "100"},
			};
			for(int i = 0; i < uriage_data.length; i++) {
				Cell cell = new Cell(new Phrase(uriage_data[i][0], font_m10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				uriage_table.addCell(new Phrase(uriage_data[i][1], font_m10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				uriage_table.addCell(cell);
				cell = new Cell(new Phrase(uriage_data[i][2], font_m10));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				uriage_table.addCell(cell);
				cell = new Cell(new Phrase(uriage_data[i][3], font_m10));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				uriage_table.addCell(cell);
			}
			Cell Empty_Cell = new Cell(new Phrase("empty", font_empty));//空行を追加
			for(int i = uriage_data.length; i < uriage_data.length + 10; i++) {
				for(int j = 0; j < 4; j++) {
					uriage_table.addCell(Empty_Cell);
				}
			}

			//合計行を出力
			Cell cell_goukei = new Cell(new Phrase("合計", font_g10));
			cell_goukei.setGrayFill(0.8f);
			cell_goukei.setColspan(3);
			uriage_table.addCell(cell_goukei);
			Cell cell_sum = new Cell(new Phrase("136,900", font_m10));
			cell_sum.setHorizontalAlignment(Element.ALIGN_RIGHT);
			uriage_table.addCell(cell_sum);

			//テーブルをドキュメントオブジェクトに追加します
			doc.add(uriage_table);

		}catch(DocumentException e) {
			e.printStackTrace();
		}
		doc.close(); //出力を終了

		//ブラウザへデータ送信
		response.setContentType("application/pdf"); //PDFを指定
		response.setContentLength(byteout.size());
		OutputStream out = response.getOutputStream();
		out.write(byteout.toByteArray());
		out.close();
	}
}
