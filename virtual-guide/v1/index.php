<?php

error_reporting(-1);

require __DIR__.'/../vendor/autoload.php';
require __DIR__.'/../db_handle/db_handler.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

//$config=['settings'=>['displayErrorDetails'=>true],];

$app=new \Slim\App(/*$config*/);

$app->get('/humayun-tomb/all', function (Request $req, Response $res) {
	$dbh=new db_handler();
	$data=$dbh->getAllData();
	return $res->withJson($data);
});

$app->get('/humayun-tomb/{id}' , function (Request $req, Response $res) {
	$id=$req->getAttribute('id');
	$dbh=new db_handler();

	$data=$dbh->getDataById($id);
	return $res->withJson($data);
});

$app->run();