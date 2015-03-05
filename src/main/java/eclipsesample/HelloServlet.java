package eclipsesample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String param1 = request.getParameter("id");
		String param2 = request.getParameter("status");
		System.out.println(param1);
		System.out.println(param2);
		
		String postResponseStr = executeGet();
		// 出力ストリーム作成
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// POSTして帰ってきた値を出力
		out.println(postResponseStr);
		out.print("Hello Servlet by Eclipse !!");
	}
	
	private static String executeGet() {
        System.out.println("===== HTTP GET Start =====");
        String result = "";

        try {
            URL url = new URL("http://localhost:8080/eclipsesample/get");

            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
                                                                       StandardCharsets.UTF_8);
                         BufferedReader reader = new BufferedReader(isr)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result = result  + line;
                        }
                    }
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("===== HTTP GET End =====");
		return result;
    }
	private void set(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// POST先のURL設定
		URL url = new URL("http://localhost:8080/eclipsesample/get");

		// 接続用HttpURLConnectionオブジェクト作成
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 出力できるようにする
		connection.setDoOutput(true);
		// キャッシュを使わないように設定
		connection.setUseCaches(false);
		// リクエストメソッドをPOSTに設定
		connection.setRequestMethod("GET");
		// POSTするパラメータの文字列設定
		//String parameterString = new String("param1=param1＆param2=param2");

		// 出力ストリーム作成
		PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
		// 生成したパラメータをPOST
		//printWriter.print(parameterString);
		// 出力ストリーム閉じる
		printWriter.close();

		// 入力ストリーム作成してPOSTのレスポンス取得
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
		String postResponseStr = new String();
		String str;
		// 入力ストリームから1行ずつ読み込む
		while ((str = bufferReader.readLine()) != null) {
		postResponseStr = postResponseStr + str;
		}
		bufferReader.close();

		// 接続用HttpURLConnection切断
		connection.disconnect();

		// 出力ストリーム作成
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// POSTして帰ってきた値を出力
		out.println(postResponseStr);
		out.print("Hello Servlet by Eclipse !!");
	}
}