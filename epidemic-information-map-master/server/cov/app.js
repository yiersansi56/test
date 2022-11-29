const { default: axios } = require('axios')
const express = require('express')
const app = express()
const port = 8090

app.use((req, res, next) => {
    if (req.path !== '/' && !req.path.includes('.')) {
      res.set({
        'Access-Control-Allow-Credentials': true,
        'Access-Control-Allow-Origin': req.headers.origin || '*',
        'Access-Control-Allow-Headers': 'X-Requested-With,Content-Type',
        'Access-Control-Allow-Methods': 'PUT,POST,GET,DELETE,OPTIONS',
        'Content-Type': 'application/json; charset=utf-8',
      })
    }
    req.method === 'OPTIONS' ? res.status(204).end() : next()
  })

app.get('/data', async (req, res) => {
const data= await axios.get('https://interface.sina.cn/news/wap/fymap2020_data.d.json').then(res=>res.data).catch(err=>err)
console.log(`api:/data ${new Date().toLocaleString()}`)
res.send(data)
})

app.listen(port, () => {
    console.log(`app listening at http://localhost:${port}`)
})
