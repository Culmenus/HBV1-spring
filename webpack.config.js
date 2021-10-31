const path = require('path');

module.exports = {
    entry: './src/main/js/index.ts',
    devtool: 'inline-source-map',
    cache: true,
    mode: 'development',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '/\.tsx?$/'),
                exclude: /node_modules/,
                use: 'ts-loader',
            }
        ]
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js']
    }
};