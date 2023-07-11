if (!"WebSocket" in window) {
  alert("Not Support WebSocket");
}

const clientId = Math.random().toString().substring(2);
// 建立websocket连接
const websocket = new WebSocket("ws://localhost:8080/ws/" + clientId);

// ----- WebSocket API -----

websocket.onerror = function () {
  setMessageInnerHTML("error");
};

websocket.onopen = function () {
  setMessageInnerHTML("connect success");
};

websocket.onmessage = function (event) {
  setMessageInnerHTML(event.data);
};

websocket.onclose = function () {
  setMessageInnerHTML("close");
};

// 监听窗口关闭
websocket.onbeforeunload = function () {
  websocket.close();
};

// -----------------------------

const setMessageInnerHTML = (innerHtml) => {
  document.getElementById("msg").innerHTML += innerHtml + "<br>";
};

const send = () => {
  websocket.send(document.getElementById("text").value);
  document.getElementById("text").value = "";
};

const closeConn = () => {
  websocket.close();
};
