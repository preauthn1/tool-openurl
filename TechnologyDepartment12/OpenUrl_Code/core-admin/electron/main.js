const { app, BrowserWindow } = require('electron');
const path = require('path')

function createWindow() {
  const win = new BrowserWindow({
    width: 1200,
    height: 800,
    autoHideMenuBar: true, // 👈 自动隐藏菜单栏
    icon: path.join(__dirname, '../build/icons/icon.ico'), // 👈 运行时图标
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      contextIsolation: true,  // 开启上下文隔离
      nodeIntegration: false   // 关闭 Node 集成，避免安全问题
    }
  });

  // 加载前端页面 (开发环境用 Vite 启动的地址，打包后用 dist)
  if (process.env.NODE_ENV === 'development') {
    win.loadURL('http://localhost:9848');
  } else {
    // win.loadFile(path.join(__dirname, '../dist/index.html'));
    win.loadURL('http://115.190.7.110');
  }
}

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
});
