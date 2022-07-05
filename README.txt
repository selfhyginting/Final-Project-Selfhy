Final Project Bus Reservation API 

Nama		: Selfhy Agina Ginting
Nomor Peserta	: JVSB001ONL011
Program		: BTDP - Back End Development With Java Springboot Hactiv8 

Deskripsi :

1. Framework yang digunakan pada aplikasi API adalah Java SpringBoot yang menggunakan beberapa depedency yaitu:
	
	- Spring Devtools : development tools yang disediakan framework SpringBoot.
	
	- Spring Web : untuk membuat aplikasi REST API. Spring Web digunakan untuk mengakses annotation @Controller pada level Controller.
	
	- Spring Data JPA : untuk mengimplementasikan JPA sehingga operasi-operasi standar seperti Create Read Update Delete sudah disediakan. 
				Spring data JPA menggunakan interface Repository yang mana menggunakan ID dari class domain sebagai parameter nya. 
				Spring data JPA juga mendukung query method, native query, JPQL dan lain sebagainya.
	
	- MySQL Driver : Driver agar aplikasi dapat terhubung ke Database dan menggunakan RDBMS berupa MySQL.
	
	- Flyway : data migration tools berfungsi melakukan migrasi data yang telah dibuat dalam file sql (query sql) ke table di database.
	
	- Lombok : menyediakan beberapa annotation untuk mempermudah pembuatan getter setter dan lainnya, salah satu yang digunakan dalam project 
			yaitu annotation @Data 

	- Spring Security : untuk dapat melakukan proses authentication dan authorization pada aplikasi.
		- untuk skema authentication menggunakan token JWT yang bertipe Bearer
		- untuk skema authorization menggunakan beberapa fungsi untuk membatasi hak akses pada semua controller yang melakukan request 
		  contoh : admin, user atau yang lainnya

	- Spring validation : menyediakan beberapa annotation yang bersifat sebagai validator salah satu yang digunakan dalam project yaitu 
					annotation @Valid untuk melakukan validasi request body pada API request

	- JWT (JSONWebToken) : 
		- untuk membangkitkan token (JSON) 
		- menambahkan batasan kedaluawarsa waktu untuk menggunakan token
		- untuk melakukan melakukan verifikasi token
		- untuk mengimplementasi skema authentication 

	- Springfox Swagger : sebagai dokumentasi dari kumpulan request yang disediakan aplikasi API.
		- terdapat deskripsi yang cukup lengkap untuk API : nama api, deskripsi api, version dan yang lainnya
		- terdapat informasi endpoint yang jelas beserta nama request methodnya 
		- terdapat schema pada kolom example yang menjadi pedoman pengguna untuk memasukkan data pada Request Body


4. Dokumentasi Swagger dapat diakses oleh pengguna untuk melakukan request pada:
	
	https://selfhy-final-project.herokuapp.com/swagger-ui.html
	
	(Mohon untuk berkenan menunggu link Swagger Final Project Selfhy hingga tampil, swagger membutuhkan waktu untuk tampil)


5. Dokumentasi penjelasan alur penggunaan aplikasi API beserta hasil screenshot dapat diakses pada:

	https://docs.google.com/document/d/1jbG3TfyHOMHtBIuNIw1I3rBJ2LlP561DXetGFKI6LR4/edit?usp=sharing

