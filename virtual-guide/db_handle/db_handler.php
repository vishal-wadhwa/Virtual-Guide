<?php

require __DIR__.'/db_connect.php';

class db_handler {

	private $mysqli_link;
	private $dbcon;
	function __construct() {
		$this->dbcon=new db_connect();
		//echo "connect";
		$this->mysqli_link=$this->dbcon->connect();

		//if ($this->mysqli_link) echo "connect";
	}

	public function getDataById($id) {
		mysqli_report(MYSQLI_REPORT_ERROR);
		$resp = array(
			"error" => true,
			"msg" => "An error occurred"
			);

		$data=$this->retrieveData($id);
		if (count($data)>0) {
			$resp['error']=false;
			$resp['msg']="Request completed";
			$resp["place"]=$data;
		}

		return $resp;
	}

	private function retrieveData($id) {
		$data=array();
		$stmt=$this->mysqli_link->prepare("SELECT * FROM visit_places WHERE id=?");
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
		mysqli_report(MYSQLI_REPORT_ERROR);
		$resp = array(
			"error" => true,
			"msg" => "An error occurred"
			);

		$stmt=$this->mysqli_link->prepare("SELECT * FROM visit_places");

		if ($stmt->execute()) {
			$resp['error']=false;
			$resp['msg']="Request completed";
			//$stmt->bind_result($id);
			//echo "execute".$resp;
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
	
	function __destruct() {
		$this->dbcon->disconnect();
	}
}

//$db=new db_handler();
//echo ($db->getDataById(2))."ok\n";