<!-- <script setup>
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <HelloWorld msg="Vite + Vue" />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style> -->

<template>
  <div id="app">
    <header>
      <h1>Outil Financier</h1>
      <nav>
        <router-link to="/" class="nav-link">Accueil</router-link>
        <router-link to="/bilan" class="nav-link">Overview</router-link>
        <router-link to="/form-transaction" class="nav-link">Formulaire de Transaction</router-link>
      </nav>
    </header>

    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style scoped>
#app {
  font-family: 'Arial', sans-serif;
  /* background-color: #f4f4f9; */
  color: #333;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}

header {
  background-color: #4CAF50;
  color: white;
  padding: 20px;
  text-align: center;
}

h1 {
  margin: 0;
  font-size: 2rem;
}

nav {
  margin-top: 10px;
}

.nav-link {
  color: white;
  margin: 0 15px;
  text-decoration: none;
  font-weight: bold;
  font-size: 1rem;
}

.nav-link:hover {
  text-decoration: underline;
}
</style>
