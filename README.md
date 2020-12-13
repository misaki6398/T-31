開發環境
========

基本款：

- Java 8
- Maven 3+

非必要：

- Eclipse
	- 應該說 `.gitignore` 會列 Eclipse 特有目錄，但是不再考慮其他 IDE... XD


Java Coding Style
=================

這裡只列天條鐵則：

- 一般縮排使用 tab
- 一般狀況下，左大括號在行尾，不另起新行
- 命名通則：cammel
	- class 名稱首字母必大寫
	- 變數、method 首字母必小寫
	- **例外**：`public static final` 變數採全大寫、單字之間用底線分隔
- public method / field 有寫 JavaDoc 的義務
	- **例外**：毫無反應只是個 getter / setter 就拜託不用再寫了

其餘可參考 [Google Java Style](https://www.ptt.cc/bbs/Translate-CS/M.1400476425.A.F83.html)
