<?php

define('RAILWAY_API_KEY','qbdtyfxc');

class railway_requests {
	private $cObj;
	private $GET_TRAIN_URL="http://api.railwayapi.com/arrivals/station/?/hours/4/apikey/".RAILWAY_API_KEY."/";

	private $defaults=array(
				CURLOPT_HEADER => 0,
				CURLOPT_RETURNTRANSFER => TRUE,
			);

	function __construct() {
		$this->cObj=curl_init();
	}

	public function getArrivingTrains($stn_code='')
	{
		# code...
		$url=str_replace('?',$stn_code,$this->GET_TRAIN_URL);
		curl_setopt($this->cObj,CURLOPT_URL,$url);
		curl_setopt_array($this->cObj,$this->defaults);
		$res=curl_exec($this->cObj);
		//echo $url;
		if (!$res) {
			$res['error']=true;
			//trigger_error($this->cObj);
		}
		//echo json_encode($res);
		//echo $res;
		//var_dump(curl_getinfo($this->cObj));
		return $res;
	}

	function __destruct() {
		curl_close($this->cObj);
	}
}

//$r=new railway_requests();
//$r->getArrivingTrains('ndls');