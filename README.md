# TP6DPBO2025C1
 
## Janji
Saya Mochamad Zidan Rusdhiana dengan NIM 2305464 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain Program

### Struktur Kelas

1. **App.java**
   - Kelas utama yang berisi method `main()` untuk memulai aplikasi
   - Menggunakan SwingUtilities untuk menjalankan aplikasi pada Event Dispatch Thread
   - Menginisialisasi dan menampilkan MainMenu

2. **MainMenu.java**
   - Kelas yang menampilkan menu utama permainan
   - Mewarisi JFrame dan berisi komponen GUI untuk menu utama
   - Memiliki tombol untuk memulai permainan
   - Menggunakan CustomBackgroundPanel untuk menampilkan latar belakang yang menarik
   - Bertanggung jawab untuk memulai permainan ketika tombol "PLAY" ditekan

3. **FlappyBird.java**
   - Kelas utama permainan yang mewarisi JPanel
   - Mengimplementasikan ActionListener dan KeyListener untuk menangani input dan pergerakan
   - Mengelola logika permainan seperti pergerakan burung, pembuatan pipa, deteksi tabrakan, dan perhitungan skor
   - Mengelola gambar dan objek permainan
   - Mengatur timer untuk animasi permainan dan pembuatan pipa

4. **Player.java**
   - Kelas yang merepresentasikan karakter burung yang dikendalikan pemain
   - Menyimpan posisi (x, y), ukuran (lebar, tinggi), gambar, dan kecepatan vertikal
   - Memiliki getter dan setter untuk semua properti

5. **Pipe.java**
   - Kelas yang merepresentasikan pipa-pipa yang harus dihindari
   - Menyimpan posisi (x, y), ukuran (lebar, tinggi), gambar, dan kecepatan horizontal
   - Menyimpan status apakah pemain sudah melewati pipa tersebut
   - Memiliki getter dan setter untuk semua properti

### Hubungan Antar Kelas

- **App** membuat dan menampilkan **MainMenu**
- **MainMenu** ketika tombol "PLAY" ditekan, akan membuat frame baru yang berisi **FlappyBird**
- **FlappyBird** mengelola objek **Player** dan koleksi objek **Pipe**
- **FlappyBird** menggunakan objek **Player** dan **Pipe** untuk menampilkan, menggerakkan, dan memeriksa tabrakan

## Cara Bermain

1. Jalankan aplikasi
2. Klik tombol "PLAY" pada menu utama
3. Tekan SPACE untuk membuat burung terbang ke atas (melawan gravitasi)
4. Navigasikan burung melalui celah di antara pipa
5. Hindari menabrak pipa atau menyentuh dasar layar
6. Skor bertambah setiap kali burung berhasil melewati sepasang pipa

## Alur Program

1. **Menu Utama**: Saat aplikasi dijalankan, menu utama ditampilkan dengan tombol "PLAY"
2. **Mulai Permainan**: Saat tombol "PLAY" ditekan, permainan dimulai
3. **Gameplay**:
   - Burung mulai dari posisi tengah layar
   - Burung akan jatuh karena gravitasi kecuali pemain menekan SPACE
   - Pipa akan muncul dari sisi kanan layar dan bergerak ke kiri
   - Pemain harus mengendalikan burung untuk terbang di antara celah pipa
   - Setiap kali burung berhasil melewati sepasang pipa, skor bertambah 1
4. **Game Over**: Permainan berakhir jika:
   - Burung menabrak pipa
   - Burung menyentuh dasar layar
5. **Restart**: Setelah game over, pemain dapat menekan "R" untuk memulai permainan baru

## Kontrol

- **SPACE**: Membuat burung terbang ke atas (melawan gravitasi)
- **R**: Memulai permainan baru setelah game over

## Dokumentasi
![GUI menu](https://github.com/user-attachments/assets/26b9c16d-78b0-4a1d-a7a9-70579b137916)
![Play](https://github.com/user-attachments/assets/aaa35221-7441-4cf8-bdbc-d05d585fffca)
![Game Over](https://github.com/user-attachments/assets/b204cc30-a025-4527-8bfe-7a6e59b4d784)


https://github.com/user-attachments/assets/06050530-7455-4de0-823e-a1fec03fe036


