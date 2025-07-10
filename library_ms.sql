-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 10 Jul 2025 pada 10.10
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_ms`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `author_list`
--

CREATE TABLE `author_list` (
  `author_id` int(20) NOT NULL,
  `author_name` varchar(60) NOT NULL,
  `book_name` varchar(60) NOT NULL,
  `address` varchar(99) NOT NULL,
  `email` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `author_list`
--

INSERT INTO `author_list` (`author_id`, `author_name`, `book_name`, `address`, `email`) VALUES
(1, 'Erlangga', 'Belajar Matematika', 'Jl. H. Baping Raya No 100, Ciracas, Jakarta, Indonesia', 'support@erlangga.co.id'),
(2, 'jk rowling', 'Harry Potter ', 'edinburgh', 'support@jk.rowling,com'),
(3, 'tere liye', 'Pulang', 'Bandar Lampung', 'tereliye.acara@gmail.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `book_details`
--

CREATE TABLE `book_details` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(300) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `book_details`
--

INSERT INTO `book_details` (`book_id`, `book_name`, `author`, `quantity`) VALUES
(1, 'c#', 'erlangga', 4),
(2, 'Belajar Python', 'Agatha christy', 6);

-- --------------------------------------------------------

--
-- Struktur dari tabel `donation`
--

CREATE TABLE `donation` (
  `id` int(11) NOT NULL,
  `donor_name` varchar(100) NOT NULL,
  `book_title` varchar(100) NOT NULL,
  `book_author` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `donation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `donation`
--

INSERT INTO `donation` (`id`, `donor_name`, `book_title`, `book_author`, `quantity`, `donation_date`) VALUES
(1, 'Crish', 'Java', 'Azka', 10000, '2025-07-01'),
(2, 'Dimas', 'PHP', 'Maul', 5000, '2025-07-02'),
(3, 'Gappy', 'Python', 'Ozi', 200000, '2025-06-11'),
(4, 'Grammy', 'Resep Masak', 'Anies', 400000, '2025-06-13'),
(5, 'zibran', 'belajar matematika', 'erlangga', 2, '2025-07-08'),
(6, 'Siti', 'belajar php', 'agatha', 5, '2025-07-10');

-- --------------------------------------------------------

--
-- Struktur dari tabel `issue_book_details`
--

CREATE TABLE `issue_book_details` (
  `id` int(11) NOT NULL,
  `book_id` int(11) DEFAULT NULL,
  `book_name` varchar(200) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `issue_book_details`
--

INSERT INTO `issue_book_details` (`id`, `book_id`, `book_name`, `student_id`, `student_name`, `issue_date`, `due_date`, `status`) VALUES
(1, 1, 'Java', 1, 'azka', '2025-05-19', '2025-05-20', 'returned'),
(2, 1, 'Java', 1, 'azka', '2025-05-14', '2025-05-17', 'returned'),
(4, 3, 'Javascripth', 3, 'fortuna', '2025-05-20', '2025-06-01', 'pending'),
(5, 2, 'Python', 2, 'maul', '2025-06-02', '2025-06-10', 'pending'),
(6, 4, 'PHP', 4, 'Dimas', '2025-06-03', '2025-06-13', 'returned'),
(7, 1, 'Java', 3, 'fortuna', '2025-06-11', '2025-06-25', 'pending'),
(8, 1, 'c#', 1, 'azka', '2025-07-10', '2025-08-10', 'returned'),
(9, 1, 'c#', 1, 'azka', '2025-07-10', '2025-08-10', 'returned');

-- --------------------------------------------------------

--
-- Struktur dari tabel `student_details`
--

CREATE TABLE `student_details` (
  `student_id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `course` varchar(200) DEFAULT NULL,
  `branch` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `student_details`
--

INSERT INTO `student_details` (`student_id`, `name`, `course`, `branch`) VALUES
(1, 'azka', 'ABC', 'IT'),
(2, 'maul', 'BSC', 'ELECTRONIC'),
(3, 'fortuna', 'MSC', 'SCIENTIC'),
(5, 'rio', '12 TKJ', 'TKJ');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contact` varchar(99) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`, `contact`) VALUES
(1, 'saya', '123456', 'saya@gmail.com', '123456789'),
(2, 'saya', '123456', 'saya@gmail.com', '123456789'),
(3, 'kamu', '12344', 'kamu@gmail.com', '987654321'),
(4, 'johnny', 'johnny', 'johnny@gmail.com', '1234567890'),
(5, 'saya', '123456', 'saya@gmail.com', '123456789'),
(6, 'perfect', '12345', 'perfect@gmail.com', '098765'),
(7, 'sola', '23456', 'sola@gmail.com', '1234509876'),
(8, 'jero', '111111', 'jero@gmail.com', '0976543'),
(9, 'jeremy', '12340', 'jeremy@gmail.com', '0864531295'),
(10, 'goblin', '09876', 'goblin@gmail.com', '0865234852'),
(11, 'zibran', '123456', 'zbrn16@gmail.com', '087747393645'),
(12, 'agatha', '123456', 'agatha@gmail.com', '08584343212');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `author_list`
--
ALTER TABLE `author_list`
  ADD PRIMARY KEY (`author_id`);

--
-- Indeks untuk tabel `book_details`
--
ALTER TABLE `book_details`
  ADD PRIMARY KEY (`book_id`);

--
-- Indeks untuk tabel `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `issue_book_details`
--
ALTER TABLE `issue_book_details`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `student_details`
--
ALTER TABLE `student_details`
  ADD PRIMARY KEY (`student_id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `book_details`
--
ALTER TABLE `book_details`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `donation`
--
ALTER TABLE `donation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `issue_book_details`
--
ALTER TABLE `issue_book_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `student_details`
--
ALTER TABLE `student_details`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
