import { createRouter, createWebHistory } from 'vue-router';

// Importer vos composants
import HelloWorld from '../components/HelloWorld.vue';
import FormTransaction from '../components/FormTransaction.vue';
import Bilan from '../components/Bilan.vue';
import Donne from '../components/Donne.vue';

const routes = [
    {
        path: '/',
        name: 'HelloWorld',
        component: HelloWorld, // Page d'accueil
    },
    {
        path: '/form-transaction',
        name: 'FormTransaction',
        component: FormTransaction, // Formulaire de transaction
    },
    {
        path: '/bilan',
        name: 'Bilan',
        component: Bilan, // Page de bilan
    },
    {
        path: '/donne',
        name: 'Donne',
        component: Donne,
    },

];

const router = createRouter({
    history: createWebHistory(), // Pour utiliser l'HTML5 History API
    routes, // Associer les routes définies ci-dessus
});

export default router;
