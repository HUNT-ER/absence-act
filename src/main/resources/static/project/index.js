const express = require('express');
const path = require('path');
const app = express();

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', async(req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'page.html'));
});

app.listen(8081, () => {
  console.log("Server successfully running on port 8081");
});