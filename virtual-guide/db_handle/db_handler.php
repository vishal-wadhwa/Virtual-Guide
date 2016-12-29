<?php

require './db_connect.php';

class db_handler {
	$mysqli_link;
	__construct() {
		$mysqli_link=new db_connect();
	}

	
	
	__destruct() {
		$mysqli_link->close();
	}
}