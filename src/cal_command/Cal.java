package cal_command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Cal {

	public static void main(String[] args) {
		
		/*************************************
		 * 初期準備
		 ************************************/
		Calendar calendar = Calendar.getInstance();
		
		// 今年の取得
		int year = calendar.get(Calendar.YEAR);
		
		// 今月の取得
		int month = calendar.get(Calendar.MONTH) + 1;
		
		// 今日の取得
		int today = calendar.get(Calendar.DATE);
		
		// 今月の月末取得
		int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 今月1日の曜日を取得
		calendar.set(year, month - 1, 1);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		// 月末の曜日を取得
		calendar.set(year, month - 1, dayOfMonth);
		int lastDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		// 背景色
		String backColBlack = "\u001b[40m";
		String backColWhite = "\u001b[47m";
		
		// 文字色
		String colBlack = "\u001b[30m";
		String colWhite = "\u001b[37m";
		
		// 色変更終了
		String colEnd = "\u001b[0m";
		
		
		/*************************************
		 * 1行目
		 ************************************/
		String header1 = "      " + month + "月 " + year + "     ";
		
		// 月が1文字の場合は最後に半角スペースを足す
		if (String.valueOf(month).length() == 1) {
			header1 = header1 + " ";
		}
		
		
		/*************************************
		 * 2行目
		 ************************************/
		String header2 = "日 月 火 水 木 金 土";
		
		
		/*************************************
		 * 3行目以降
		 ************************************/
		int week = 0;
		int countDay = 1;
		String day = "";
		List<String> lineList = new ArrayList<>();
		
		for (int i = 1; i <= dayOfMonth; i++) {
			
			// 第一週目が日曜以外
			if (i == 1 && dayOfWeek != 1) {
				lineList.add(backColBlack + colWhite + String.join("" , Collections.nCopies(dayOfWeek - 1, "   ")));
				countDay = dayOfWeek;
			}
			
			// 日にち変換
			if (i == today) {
				day = backColWhite + colBlack + String.format("%2d", i) + backColBlack + colWhite;
			}
			else {
				day = String.format("%2d", i);
			}
			
			// 日曜日
			if (countDay == 1) {
				lineList.add(backColBlack + colWhite + day + " ");
				countDay++;
			}
			// 土曜日
			else if (countDay == 7) {
				lineList.set(week, lineList.get(week) + day + colEnd);
				countDay = 1;
				week++;
			}
			// 月～金曜日
			else {
				lineList.set(week, lineList.get(week) + day + " ");
				countDay++;
			}

		}
		
		
		/*************************************
		 * 月末の調整
		 ************************************/
		int endLine = lineList.size() - 1;		
		int absLastDayOfWeek = lastDayOfWeek - 6;
		
		// 最終週が日～金曜日で終わりの場合にスペースを追加
		if (absLastDayOfWeek < 1) {
			lineList.set(
				endLine,
				lineList.get(endLine)
					+ "  "
					+ String.join("", Collections.nCopies(Math.abs(absLastDayOfWeek), "   "))
					+ colEnd
			);
		}
		
		
		/*************************************
		 * 出力
		 ************************************/
		System.out.println(backColBlack + colWhite + header1 + colEnd);
		System.out.println(backColBlack + colWhite + header2 + colEnd);
		lineList.forEach(line -> System.out.println(line));

	}

}
