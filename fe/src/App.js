import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import BeforeLogin from './components/BeforeLogin';
import AfterLogin from './components/AfterLogin';

function App() {
  return (
    <BrowserRouter>
      <main>
        <Routes>
          <Route path='/oauth/twitter' element={<AfterLogin />} />
          <Route path='/' element={<Home />}/>
        </Routes>
        
      </main>
    </BrowserRouter>
  );
}

export default App;
