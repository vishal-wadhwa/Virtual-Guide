<?php

require './db_connect.php';

class db_handler {
	$mysqli_link;
	__construct() {
		$mysqli_link=new db_connect();
	}

	public function getDataById($id) {
		$resp = array(
			"error" => true;
			"msg" => "An error occurred"
			);

		$data=retrieveData($id);
		if (count($data)>0) {
			$resp['error']=false;
			$resp['msg']="Request completed";
			$resp["place"]=$data;
		}

		return $resp;
	}

	private function retrieveData($id) {
		$data=array();
		$stmt=$mysqli_link->prepare("SELECT * from visit_places WHERE id=?");
		$stmt->bind_param('i',$id);
		if ($stmt->execute()) {
			$stmt->bind_result(
				$data['id'],
				$data['name'],
				$data['info'],
				$data['audio_url'],
				$data['image_url']);

			$stmt->fetch();
		}

		$stmt->close();
		return $data;
	}

	public function getAllData() {
		$resp = array(
			"error" => true;
			"msg" => "An error occurred"
			);

		$stmt=$mysqli_link->prepare("SELECT * FROM visit_places");

		if ($stmt->execute()) {
			$resp['error']=false;
			$resp['msg']="Request completed";
			//$stmt->bind_result($id);
			$places=array();
			$i=0;
			$res=$stmt->get_result();

			while($row = $res->fetch_array(MYSQLI_ASSOC)) {
				//$data=retrieveData($id);
				echo $row;
				$places[$i]=$row;
				$i++;
			}

			$resp['places']=$places;
		}

		$stmt->close();
		return $resp;

	}
	
	__destruct() {
		$mysqli_link->close();
	}
}