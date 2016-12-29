<?php

require './db_config.php';
class db_connect {
	$conn;

	__construct() {
		
	}
	public function connect() {
		$conn=new mysqli(DB_HOST,USER,PASSWORD,DB_NAME);
		if ($conn->connect_errno) {
			die('Connect Error: '.$conn->connect_errno);
		}
		return $conn;
	}

	public function disconnect() {
		$conn->close();
	}
}