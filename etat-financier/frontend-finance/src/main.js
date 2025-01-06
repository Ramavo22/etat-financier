// import { createApp } from 'vue'
// import './style.css'
// import App from './App.vue'

// createApp(App).mount('#app')

import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Importer le routeur

const app = createApp(App);

app.use(router); // Utiliser le routeur

app.mount('#app');
