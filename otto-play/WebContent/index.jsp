<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
  <script type='text/javascript' src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
  
  <!-- A polyfill for Promise is needed for IE11 support -->
  <script type='text/javascript' src='https://cdn.jsdelivr.net/npm/promise-polyfill@8/dist/polyfill.min.js'></script>
  
  <script src="https://kit.fontawesome.com/c3d3b329a2.js" crossorigin="anonymous"></script>
  <script src="resources/js/scripts.js"></script>
  
  <link rel="stylesheet" type="text/css" href="resources/css/estilo.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;
		0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;
		1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
  <title>Árvore de Livros</title>
</head>
<body>
  <main>
    <object id="book" data="resources/livro/livro1.pdf" type="application/pdf" name="livro1.pdf" ></object>
    
    <img id="otto" src="resources/img/otto2.png" alt="Macaco Otto">
    <div id="audition">
      <a onclick="playHide()" class="play-pause-visible" id="icon-play">
        <i class="fas fa-play-circle fa-fw fa-2x"></i>
      </a>

      <a onclick="pauseHide()" class="play-pause-hide" id="icon-pause" href="#">
        <i class="fas fa-pause-circle fa-fw fa-2x"></i>
      </a>
      <span id="tooltip">Ouvir o Otto lendo a página.</span>
    </div>
  </main>
</body>
</html>